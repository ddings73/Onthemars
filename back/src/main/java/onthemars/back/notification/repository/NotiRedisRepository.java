package onthemars.back.notification.repository;

import java.util.List;
import onthemars.back.notification.domain.NotificationRedis;
import org.springframework.data.repository.CrudRepository;

public interface NotiRedisRepository extends CrudRepository<NotificationRedis, String> {
    List<NotificationRedis> findAllByMemberAddress(String address);
}
