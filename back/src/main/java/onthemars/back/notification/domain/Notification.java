package onthemars.back.notification.domain;

import com.sun.istack.NotNull;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import onthemars.back.notification.app.NotiTitle;
import onthemars.back.user.domain.Member;
import org.hibernate.annotations.DynamicInsert;

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
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_address", nullable = false, columnDefinition = "char")
    @ToString.Exclude
    private @NotNull Member member;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private @NotNull NotiTitle title;
    @Column(nullable = false)
    private @NotNull String content;

    @Column(nullable = false)
    private @NotNull LocalDateTime regDt;

    @Column(nullable = false)
    private @NotNull Boolean verified;

    @Column(nullable = false)
    private @NotNull Boolean deleted;

    public NotificationRedis toRedisEntity(){
        return NotificationRedis.builder()
            .id(id)
            .address(member.getAddress())
            .title(title)
            .content(content)
            .regDt(regDt)
            .verified(verified)
            .expiration(86400L)
            .build();
    }

    public void verify(){
        this.verified = true;
    }
    public void delete() {
        this.deleted = true;
    }
}
