package onthemars.back.user.app;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString @Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenInfo {
    private String grantType;
    private String accessToken;
    private String refreshToken;

    public static TokenInfo of(String grantType, String accessToken, String refreshToken) {
        return new TokenInfo(grantType, accessToken, refreshToken);
    }

}
