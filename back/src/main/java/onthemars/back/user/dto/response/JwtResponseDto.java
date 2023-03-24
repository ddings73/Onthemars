package onthemars.back.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import onthemars.back.user.app.TokenInfo;
import onthemars.back.user.domain.Profile;

@Getter @Setter
@AllArgsConstructor
public class JwtResponseDto {
    private String accessToken;
    private String refreshToken;
    private String nickname;
    private String profileImgUrl;

    public static JwtResponseDto of(TokenInfo tokenInfo, Profile profile){
        String accessToken = tokenInfo.getGrantType() + tokenInfo.getAccessToken();
        String refreshToken = tokenInfo.getGrantType() + tokenInfo.getRefreshToken();
        return new JwtResponseDto(
            accessToken, refreshToken, profile.getNickname(), profile.getProfileImg()
        );
    }
}
