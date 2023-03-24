package onthemars.back.user.repository;

import onthemars.back.user.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, String> {

}
