package onthemars.back.user.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import onthemars.back.user.domain.Profile;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponseDto {
    private UserInfo user;

    public static ProfileResponseDto of(Profile profile){
        UserInfo user = UserInfo.of(profile);
        return new ProfileResponseDto(user);
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private static class UserInfo{
        private String address;
        private String nickname;
        private String profileImg;
        private LocalDateTime regDt;

        public static UserInfo of(Profile profile){
            return UserInfo.builder()
                .address(profile.getAddress())
                .nickname(profile.getNickname())
                .profileImg(profile.getProfileImg())
                .regDt(profile.getRegDt())
                .build();
        }
    }
}
