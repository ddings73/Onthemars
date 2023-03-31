package onthemars.back.notification.repository;

import onthemars.back.notification.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotiRepository extends JpaRepository<Notification, Long> {

}
