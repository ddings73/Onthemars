package onthemars.back.nft.repository;

import java.time.LocalDateTime;
import java.util.Optional;
import onthemars.back.nft.entity.NftHistory;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.Nullable;

public interface NftHistoryRepository extends PagingAndSortingRepository<NftHistory, Long> {

    NftHistory findFirstByOrderByRegDtDesc();

}
