package onthemars.back.notification.dto.request;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import onthemars.back.notification.domain.Notification;
import onthemars.back.user.domain.Member;

@ToString
@Getter @Setter
@NoArgsConstructor
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

    public Notification toEntity(Member member) {
        return Notification.builder()
            .member(member)
            .content(content)
            .regDt(regDt)
            .verified(false)
            .deleted(false)
            .build();
    }
}
