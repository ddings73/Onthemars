package onthemars.back.nft.repository;

import onthemars.back.nft.entity.NftHistory;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NftHistoryRepository extends PagingAndSortingRepository<NftHistory, Long> {

}
