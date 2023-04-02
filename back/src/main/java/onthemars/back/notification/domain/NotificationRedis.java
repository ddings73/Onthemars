package onthemars.back.notification.domain;


import com.sun.istack.NotNull;
import java.time.LocalDateTime;
import javax.persistence.Id;

import lombok.*;
import onthemars.back.notification.app.NotiTitle;
import onthemars.back.notification.dto.request.NotiRequestDto;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter @RedisHash("notification")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRedis{

    @Id
    private Long id;

    @Indexed
    private @NotNull String address;

    private @NotNull
    NotiTitle title;
    private @NotNull String content;
    private @NotNull LocalDateTime regDt;

    @TimeToLive
    private Long expiration;

    public static NotificationRedis create(Long id, NotiRequestDto requestDto, Long expiration) {
        return NotificationRedis.builder()
            .id(id)
            .address(requestDto.getAddress())
            .title(requestDto.getTitle())
            .content(requestDto.getContent())
            .regDt(requestDto.getRegDt())
            .expiration(expiration)
            .build();
    }
}
