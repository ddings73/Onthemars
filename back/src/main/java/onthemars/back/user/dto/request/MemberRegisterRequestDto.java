package onthemars.back.user.dto.request;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import onthemars.back.user.domain.Profile;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegisterRequestDto {
    private String address;
    private String nickname;
    private MultipartFile profileImgFile;

    public Profile toMemberProfile(String profileImgUrl){
        return Profile.builder()
            .address(address)
            .nickname(nickname)
            .profileImg(profileImgUrl)
            .seedCnt(0)
            .regDt(LocalDateTime.now())
            .build();
    }
}
