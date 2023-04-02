package onthemars.back.notification.repository;

import java.util.Optional;
import onthemars.back.notification.domain.NotificationRedis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface NotiRedisRepository extends CrudRepository<NotificationRedis, Long> {
    Optional<NotificationRedis> findByAddress(String address);

    Page<NotificationRedis> findAllByAddress(String address, Pageable pageable);
}
