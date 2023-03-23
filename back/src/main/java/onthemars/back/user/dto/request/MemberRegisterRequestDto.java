package onthemars.back.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegisterRequestDto {
    private String address;
    private String nickname;
    private MultipartFile profileImgFile;
}
