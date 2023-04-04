package onthemars.back.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import onthemars.back.user.domain.Member;

@Getter
@Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {
    private String nickname;
    private String profileImgUrl;
    private Boolean receive;

    public static LoginResponseDto of(JwtResponseDto jwtResponse) {
        return new LoginResponseDto(jwtResponse.getNickname(), jwtResponse.getProfileImgUrl(), jwtResponse.getReceive());
    }
}
