package onthemars.back.notification.domain;

import com.sun.istack.NotNull;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import onthemars.back.notification.dto.request.NotiRequestDto;
import onthemars.back.user.domain.Member;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@DynamicInsert
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @NotNull Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_address", nullable = false, columnDefinition = "char")
    @ToString.Exclude
    private @NotNull Member member;

    @Column(nullable = false)
    private @NotNull String content;

    @Column(nullable = false)
    private @NotNull LocalDateTime regDt;

    @Column(nullable = false)
    private @NotNull Boolean verified;

    @Column(nullable = false)
    private @NotNull Boolean deleted;

    public static Notification of(NotiRequestDto requestDto, Member member){
        return Notification.builder()
            .member(member)
            .content(requestDto.getContent())
            .regDt(requestDto.getRegDt())
            .verified(false)
            .deleted(false)
            .build();
    }
}
