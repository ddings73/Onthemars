package onthemars.back.user.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponseDto {
    private String address;
    private String nickname;
    private String profileImgUrl;
    private LocalDateTime regDt;
}
