package onthemars.back.notification.repository;

import onthemars.back.notification.domain.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotiRepository extends JpaRepository<Notification, Long> {

    Page<Notification> findAllByMemberAddressAndDeletedIsFalse(String address, Pageable pageable);

    Boolean existsByMemberAddressAndVerifiedIsFalse(String address);
}
