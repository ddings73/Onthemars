package onthemars.back.nft.repository;

import java.util.List;
import java.util.Optional;
import onthemars.back.nft.entity.NftHistory;
import onthemars.back.user.domain.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NftHistoryRepository extends PagingAndSortingRepository<NftHistory, Long> {

    List<NftHistory> findBySellerOrBuyerOrderByRegDtDesc(Profile seller, Profile buyer,
        Pageable pageable);

    Optional<NftHistory> findFirstByNft_TypeAndEventTypeOrderByPriceDesc(String type,
        String eventType);

    List<NftHistory> findByNft_TypeAndEventType(String type, String eventType);

    NftHistory findFirstByOrderByRegDtDesc();

    List<NftHistory> findByNft_AddressOrderByRegDtDesc(String address, Pageable pageable);

    Optional<NftHistory> findFirstByNft_AddressAndEventTypeOrderByRegDtDesc(
        String address, String eventType);

    List<NftHistory> findByBuyer_AddressAndEventTypeOrderByRegDtDesc(
        String address, String eventType, Pageable pageable);

}