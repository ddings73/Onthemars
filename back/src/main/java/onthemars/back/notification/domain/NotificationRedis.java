package onthemars.back.notification.domain;


import com.sun.istack.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import onthemars.back.notification.dto.request.NotiRequestDto;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter @RedisHash("notification")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRedis {

    @Id
    private Long id;
    private @NotNull String address;
    private @NotNull String title;
    private @NotNull String content;

    @Indexed
    private @NotNull LocalDateTime regDt;

    @TimeToLive
    private Long expiration;

    public static NotificationRedis create(NotiRequestDto requestDto, Long expiration) {
        return NotificationRedis.builder()
            .address(requestDto.getAddress())
            .title(requestDto.getTitle())
            .content(requestDto.getContent())
            .regDt(requestDto.getRegDt())
            .expiration(expiration)
            .build();
    }
}
