package onthemars.back.user.service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.xml.xpath.XPath;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onthemars.back.aws.AwsS3Utils;
import onthemars.back.aws.S3Dir;
import onthemars.back.common.security.JwtProvider;
import onthemars.back.common.security.SecurityUtils;
import onthemars.back.exception.IllegalSignatureException;
import onthemars.back.exception.UserNotFoundException;
import onthemars.back.user.app.TokenInfo;
import onthemars.back.user.domain.Member;
import onthemars.back.user.domain.Profile;
import onthemars.back.user.dto.request.AuthRequestDto;
import onthemars.back.user.dto.request.MemberRegisterRequestDto;
import onthemars.back.user.dto.response.JwtResponseDto;
import onthemars.back.user.dto.response.LoginResponseDto;
import onthemars.back.user.dto.response.NonceResponseDto;
import onthemars.back.user.repository.ProfileRepository;
import onthemars.back.user.repository.MemberRepository;
import onthemars.back.user.util.EtherUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.web3j.crypto.WalletUtils;

@Service @Slf4j
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final String PROFILE_DEFAULT_URL = "noImage.png";
    private final RedisTemplate<String, String> redisTemplate;
    private final JwtProvider jwtProvider;
    private final AwsS3Utils awsS3Utils;
    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;

    public void registerUser(MemberRegisterRequestDto request) {
        String address = request.getAddress();
        verifyAddress(address);

        MultipartFile profileImg = request.getProfileImgFile();
        String profileImgUrl = awsS3Utils.upload(profileImg, S3Dir.PROFILE)
            .orElse(S3Dir.PROFILE.getPath() + PROFILE_DEFAULT_URL);

        Profile profile = request.toMemberProfile(profileImgUrl);
        profileRepository.save(profile);
    }
    public NonceResponseDto loginUser(String address) {
        memberRepository.findById(address).orElseThrow(UserNotFoundException::new);
        String nonce = randNonce();

        redisTemplate.opsForHash().put(address, "nonce", nonce);
        redisTemplate.expire(address, 1, TimeUnit.HOURS);

        return new NonceResponseDto(address, nonce);
    }

    public JwtResponseDto authUser(AuthRequestDto requestDto) {
        String address = requestDto.getAddress();
        verifyAddress(address);

        Profile profile = profileRepository.findById(address).orElseThrow(UserNotFoundException::new);
        veritySignature(address, requestDto.getSignature());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
            profile, null, AuthorityUtils.createAuthorityList("ROLE_USER"));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        TokenInfo tokenInfo = jwtProvider.generateToken(authentication);
        return JwtResponseDto.of(tokenInfo, profile);
    }

    public void logOut(){
        String address = SecurityUtils.getCurrentUserId();
        redisTemplate.delete(address);
    }

    private String randNonce(){
        return String.valueOf(Math.floor(Math.random() * 1_000_000));
    }
    private void verifyAddress(String address) {
        if(!WalletUtils.isValidAddress(address))
            throw new IllegalArgumentException();
    }

    private void veritySignature(String address, String signature){
        String nonce = String.valueOf(redisTemplate.opsForHash().get(address, "nonce"));
        String findAddress = EtherUtils.recoverSignature(signature, nonce);

        if(!findAddress.equals(address))
            throw new IllegalSignatureException();
    }
}
