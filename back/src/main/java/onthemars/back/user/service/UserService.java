package onthemars.back.user.service;

import lombok.RequiredArgsConstructor;
import onthemars.back.aws.AwsS3Utils;
import onthemars.back.aws.S3Dir;
import onthemars.back.common.security.SecurityUtils;
import onthemars.back.exception.UserNotFoundException;
import onthemars.back.user.domain.Profile;
import onthemars.back.user.dto.request.UpdateNicknameRequestDto;
import onthemars.back.user.dto.response.ProfileResponseDto;
import onthemars.back.user.repository.MemberRepository;
import onthemars.back.user.repository.ProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final ProfileRepository profileRepository;
    private final AwsS3Utils awsS3Utils;
    public ProfileResponseDto findUserProfile(String address) {
        Profile profile = profileRepository.findById(address)
            .orElseThrow(UserNotFoundException::new);

        return ProfileResponseDto.of(profile);
    }


    public void uploadProfile(MultipartFile profileImgFile) {
        String address = SecurityUtils.getCurrentUserId();
        Profile profile = profileRepository.findById(address)
            .orElseThrow(UserNotFoundException::new);

        String profileImgUrl = profile.getProfileImg();

        awsS3Utils.upload(profileImgFile, address, S3Dir.PROFILE).orElse(profileImgUrl);
    }

    public void updateNickname(UpdateNicknameRequestDto requestDto) {
        String address = SecurityUtils.getCurrentUserId();
        Profile profile = profileRepository.findById(address)
            .orElseThrow(UserNotFoundException::new);

        String nickname = requestDto.getNickname();
        profile.updateNickname(nickname);
    }
}
