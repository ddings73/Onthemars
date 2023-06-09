package onthemars.back.user.service;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onthemars.back.aws.AwsS3Utils;
import onthemars.back.aws.S3Dir;
import onthemars.back.common.security.JwtProvider;
import onthemars.back.common.security.SecurityUtils;
import onthemars.back.exception.IllegalSignatureException;
import onthemars.back.exception.UserNotFoundException;
import onthemars.back.farm.service.FarmService;
import onthemars.back.notification.repository.NotiRepository;
import onthemars.back.user.app.TokenInfo;
import onthemars.back.user.domain.Profile;
import onthemars.back.user.dto.request.AuthRequestDto;
import onthemars.back.user.dto.request.LoginRequestDto;
import onthemars.back.user.dto.request.MemberRegisterRequestDto;
import onthemars.back.user.dto.response.JwtResponseDto;
import onthemars.back.user.dto.response.NonceResponseDto;
import onthemars.back.user.repository.MemberRepository;
import onthemars.back.user.repository.ProfileRepository;
import onthemars.back.user.util.EtherUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.web3j.abi.datatypes.Bool;
import org.web3j.crypto.WalletUtils;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final String PROFILE_DEFAULT_URL = "profilenoImage.png";
    private final RedisTemplate<String, String> redisTemplate;
    private final JwtProvider jwtProvider;
    private final AwsS3Utils awsS3Utils;
    private final FarmService farmService;
    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;
    private final NotiRepository notiRepository;

    public void registerUser(MemberRegisterRequestDto request) {
        String address = request.getAddress();
        verifyAddress(address);

        memberRepository.findById(address).ifPresent(member -> {
            throw new IllegalArgumentException();
        });

        MultipartFile profileImg = request.getProfileImgFile();

        String profileImgUrl = awsS3Utils.upload(profileImg, address, S3Dir.PROFILE)
            .orElse("/" + S3Dir.PROFILE.getPath() + "/" + PROFILE_DEFAULT_URL);

        Profile profile = request.toMemberProfile(profileImgUrl);
        profileRepository.save(profile);

    }

    public NonceResponseDto loginUser(LoginRequestDto requestDto) {
        String address = requestDto.getAddress();

        memberRepository.findById(address).orElseThrow(UserNotFoundException::new);
        String nonce = randNonce();

        redisTemplate.opsForValue().set(address, nonce);
        redisTemplate.expire(address, 1, TimeUnit.HOURS);

        return new NonceResponseDto(address, nonce);
    }

    public JwtResponseDto authUser(AuthRequestDto requestDto) {
        String address = requestDto.getAddress();
        verifyAddress(address);

        Profile profile = profileRepository.findById(address)
            .orElseThrow(UserNotFoundException::new);
        veritySignature(address, requestDto.getSignature());

        Boolean receive = notiRepository.existsByMemberAddressAndVerifiedIsFalse(address);
        receive = farmService.cropGrowth(profile) | receive;
        return getTokenWithProfile(profile, receive);
    }


    public void logoutUser(String refreshToken) {
        redisTemplate.opsForValue().getAndDelete(refreshToken);
    }

    public JwtResponseDto reissueToken(String accessToken, String refreshToken) {
        String rt = refreshToken.substring(6);
        String at = accessToken.substring(6);

        String address = redisTemplate.opsForValue().getAndDelete(rt);

        if(!jwtProvider.validateToken(rt) || jwtProvider.validateToken(at) || address == null){
            throwBadCredential(address, rt);
        }

        Profile profile = profileRepository.findById(address)
            .orElseThrow(UserNotFoundException::new);

        Boolean receive = notiRepository.existsByMemberAddressAndVerifiedIsFalse(address);
        return getTokenWithProfile(profile, receive);
    }

    public String findCurrentUserAddress() {
        return SecurityUtils.getCurrentUserId();
    }

    public String findCurrentOrAnonymousUser() {
        return SecurityUtils.getCurrentOrAnonymousUser();
    }

    private void throwBadCredential(String address, String refreshToken){
        redisTemplate.delete(address);
        redisTemplate.delete(refreshToken);
        throw new BadCredentialsException("다시 로그인 해주세요.");
    }
    private JwtResponseDto getTokenWithProfile(Profile profile, Boolean receive){
        Authentication authentication = new UsernamePasswordAuthenticationToken(
            profile.getAddress(), null, AuthorityUtils.createAuthorityList("ROLE_USER"));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        TokenInfo tokenInfo = jwtProvider.generateToken(authentication);

        return JwtResponseDto.of(tokenInfo, profile, receive);
    }

    private String randNonce() {
        return String.valueOf(Math.floor(Math.random() * 1_000_000));
    }

    private void verifyAddress(String address) {
        if (!WalletUtils.isValidAddress(address)) {
            throw new IllegalArgumentException();
        }
    }

    private void veritySignature(String address, String signature) {
        String nonce = String.valueOf(redisTemplate.opsForValue().get(address));
        String findAddress = EtherUtils.recoverSignature(signature, nonce);

        if (!findAddress.equals(address)) {
            throw new IllegalSignatureException();
        }
    }
}
