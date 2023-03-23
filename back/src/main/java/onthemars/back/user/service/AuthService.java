package onthemars.back.user.service;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onthemars.back.common.security.JwtProvider;
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
    private final String PROFILE_DEFAULT_URL = "noProfile.png";
    private final RedisTemplate<String, String> redisTemplate;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;

    public void registerUser(MemberRegisterRequestDto request) {
        String address = request.getAddress();
        verifyAddress(address);

        // 닉네임 중복체크 넣어야함 Conflict

        String profileImgUrl = PROFILE_DEFAULT_URL;
        MultipartFile profileImg = request.getProfileImgFile();
        if(profileImg != null){

        }

        Profile profile = request.toMemberProfile(profileImgUrl);
        profileRepository.save(profile);
    }
    public NonceResponseDto loginUser(String address) {
        memberRepository.findById(address).orElseThrow(UserNotFoundException::new);
        String nonce = randNonce();

        ValueOperations<String, String> vo = redisTemplate.opsForValue();
        vo.set(address, nonce);
        redisTemplate.expire(address, 1, TimeUnit.HOURS);

        return new NonceResponseDto(address, nonce);
    }

    public JwtResponseDto authUser(AuthRequestDto requestDto) {
        String address = requestDto.getAddress();
        verifyAddress(address);

        Profile profile = profileRepository.findById(address).orElseThrow(UserNotFoundException::new);

        ValueOperations<String, String> vo = redisTemplate.opsForValue();
        String nonce = vo.get(address);

        String signature = requestDto.getSignature();
        String findAddress = EtherUtils.recoverSignature(signature, nonce);

        if(!findAddress.equals(address))
            throw new IllegalSignatureException();

        Authentication authentication = new UsernamePasswordAuthenticationToken(
            profile, null, AuthorityUtils.createAuthorityList("ROLE_USER"));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        TokenInfo tokenInfo = jwtProvider.generateToken(authentication);
        return JwtResponseDto.of(tokenInfo, profile);
    }
    private String randNonce(){
        return String.valueOf(Math.floor(Math.random() * 1_000_000));
    }
    private void verifyAddress(String address) {
        if(!WalletUtils.isValidAddress(address))
            throw new IllegalArgumentException();
    }
}
