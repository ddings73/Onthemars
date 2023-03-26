package onthemars.back.nft.repository;

import java.util.List;
import java.util.Optional;
import onthemars.back.nft.entity.NftHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NftHistoryRepository extends PagingAndSortingRepository<NftHistory, Long> {

    Optional<NftHistory> findFirstByNft_TypeAndEventTypeOrderByPriceDesc(String type, String eventType);

    List<NftHistory> findByNft_TypeAndEventType(String type, String eventType);

    NftHistory findFirstByOrderByRegDtDesc();

    List<NftHistory> findByNft_AddressOrderByRegDtDesc(String address, Pageable pageable);

    Optional<NftHistory> findFirstByNft_AddressAndEventTypeOrderByRegDtDesc(String address, String eventType);

}