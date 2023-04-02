package onthemars.back.notification.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import onthemars.back.notification.app.NotiTitle;
import onthemars.back.notification.domain.Notification;
import onthemars.back.notification.domain.NotificationRedis;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AlarmListResponseDto {
    private int totalPages;
    private long totalElements;
    private List<AlarmInfo> alarms;


    public static AlarmListResponseDto toDtoWithJpaPages(Page<Notification> jpaPages){

        return new AlarmListResponseDto(jpaPages.getTotalPages(), jpaPages.getTotalElements(), null);
    }

    public static AlarmListResponseDto toDtoWithRedisPages(Page<NotificationRedis> redisPages){
        List<AlarmInfo> alarms = redisPages.stream().map(AlarmInfo::ofRedis).collect(Collectors.toList());
        return new AlarmListResponseDto(redisPages.getTotalPages(), redisPages.getTotalElements(), alarms);
    }

    @AllArgsConstructor
    private static class AlarmInfo{
        private Long id;
        private NotiTitle title;
        private String content;
        private LocalDateTime regDt;

        public static AlarmInfo ofRedis(NotificationRedis nr){
            return new AlarmInfo(nr.getId(), nr.getTitle(), nr.getContent(), nr.getRegDt());
        }
    }
}
