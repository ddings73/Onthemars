package onthemars.back.notification.dto.request;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter @Setter
public class NotiRequestDto {
    private String address;
    private String title;
    private String content;
    private LocalDateTime regDt;

    public NotiRequestDto(String address, String title, String content){
        this.address = address;
        this.title = title;
        this.content = content;
        this.regDt = LocalDateTime.now();
    }
}
