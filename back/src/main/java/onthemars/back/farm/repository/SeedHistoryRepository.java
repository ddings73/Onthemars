package onthemars.back.farm.repository;

import onthemars.back.farm.domain.SeedHistory;
import onthemars.back.user.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeedHistoryRepository extends JpaRepository<SeedHistory, Long> {

    Long countByMember(Member member);
}
