package onthemars.back.nft.repository;

import java.util.List;
import java.util.Optional;
import onthemars.back.nft.entity.NftHistory;
import onthemars.back.user.domain.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NftHistoryRepository extends PagingAndSortingRepository<NftHistory, Long> {

    List<NftHistory> findByTransaction_IdAndEventTypeOrderByRegDtAsc(Long id, String type);

    Optional<NftHistory> findFirstByTransaction_DnaStartsWithOrTransaction_DnaStartsWithAndEventTypeOrderByTransaction_PriceDesc(
        String dna, String dna1, String eventType);

    List<NftHistory> findByTransaction_DnaStartsWithOrTransaction_DnaStartsWith(
        String dna1, String dna2);

    List<NftHistory> findByTransaction_IdAndEventType(Long id, String eventType);

    List<NftHistory> findByTransaction_IdOrderByRegDtDesc(Long id, Pageable pageable);

    Optional<NftHistory> findFirstByTransaction_IdOrderByRegDtDesc(Long id);

    Optional<NftHistory> findFirstByTransaction_IdAndEventTypeOrderByRegDtDesc(Long id,
        String eventType);

    List<NftHistory> findBySellerOrBuyerOrderByRegDtDesc(Profile seller, Profile buyer,
        Pageable pageable);

    NftHistory findFirstByOrderByRegDtDesc();

    List<NftHistory> findByBuyer_AddressAndEventTypeOrderByRegDtDesc(
        String address, String eventType, Pageable pageable);

}