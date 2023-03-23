package onthemars.back.user.repository;

import onthemars.back.user.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Member, String> {

}
