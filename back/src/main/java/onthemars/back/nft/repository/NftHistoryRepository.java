package onthemars.back.nft.repository;

import java.util.List;
import onthemars.back.nft.entity.NftHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NftHistoryRepository extends PagingAndSortingRepository<NftHistory, Long> {

    NftHistory findFirstByOrderByRegDtDesc();

    List<NftHistory> findByNft_AddressOrderByRegDtDesc(String address, Pageable pageable);


}
