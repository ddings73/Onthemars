package onthemars.back.notification.dto.request;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import onthemars.back.notification.app.NotiTitle;
import onthemars.back.notification.domain.Notification;
import onthemars.back.user.domain.Member;

@ToString
@Getter @Setter
@NoArgsConstructor
public class NotiRequestDto {
    private String address;
    private NotiTitle title;
    private String content;
    private LocalDateTime regDt;
    private Long expiration;

    public NotiRequestDto(String address, NotiTitle title, String content, Long expiration){
        this.address = address;
        this.title = title;
        this.content = content;
        this.regDt = LocalDateTime.now();
        this.expiration = expiration;
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
