package onthemars.back.notification.repository;

import java.util.Optional;
import onthemars.back.firebase.FcmToken;
import org.springframework.data.repository.CrudRepository;

public interface FcmTokenRepository extends CrudRepository<FcmToken, Long> {

    Optional<FcmToken> findByAddress(String address);
}
