package onthemars.back.code.repository;

import onthemars.back.code.domain.Code;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeRepository extends JpaRepository<Code, String> {

}
