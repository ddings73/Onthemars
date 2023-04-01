package onthemars.back.notification.repository;

import java.util.Optional;
import onthemars.back.notification.domain.FcmToken;
import org.springframework.data.repository.CrudRepository;

public interface FcmTokenRepository extends CrudRepository<FcmToken, String> {

    Optional<FcmToken> findByAddress(String address);
}
