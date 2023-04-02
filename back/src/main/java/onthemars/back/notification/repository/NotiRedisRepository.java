package onthemars.back.notification.repository;

import java.util.List;
import java.util.Optional;
import onthemars.back.notification.domain.NotificationRedis;
import org.springframework.data.repository.CrudRepository;

public interface NotiRedisRepository extends CrudRepository<NotificationRedis, Long> {

    Optional<NotificationRedis> findByAddress(String address);
}
