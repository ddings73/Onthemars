package onthemars.back.farm.repository;

import java.util.List;
import onthemars.back.farm.domain.Crop;
import onthemars.back.user.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CropRepository extends JpaRepository<Crop, Long> {
    List<Crop> findAllByMemberAndPotNumIsNotNull(Member member);
}