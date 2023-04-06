package onthemars.back.nft.repository;

import java.util.List;
import java.util.Optional;
import onthemars.back.nft.entity.NftHistory;
import onthemars.back.user.domain.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface NftHistoryRepository extends PagingAndSortingRepository<NftHistory, Long> {
    List<NftHistory> findByTransaction_Id(Long id);

    List<NftHistory> findByTransaction_IdAndEventTypeOrderByRegDtAsc(Long id, String type);

    @Query("SELECT MAX(e) FROM NftHistory e WHERE SUBSTRING(e.transaction.dna, 3, 1) = :codeNum AND e.eventType = :eventType ORDER BY e.transaction.price DESC")
    Optional<NftHistory> findFloorPriceNftHistory(
        @Param("codeNum") String codeNum, @Param("eventType") String eventType);

    @Query("SELECT e FROM NftHistory e WHERE SUBSTRING(e.transaction.dna, 3, 1) = :codeNum")
    List<NftHistory> findByCropNum(@Param("codeNum") String codeNum);

    List<NftHistory> findByTransaction_IdAndEventType(Long id, String eventType);

    List<NftHistory> findByTransaction_IdOrderByRegDtDesc(Long id, Pageable pageable);

    Optional<NftHistory> findFirstByTransaction_IdOrderByRegDtDesc(Long id);

    Optional<NftHistory> findFirstByTransaction_IdAndEventTypeOrderByRegDtDesc(Long id,
        String eventType);

    List<NftHistory> findBySellerOrBuyerOrderByRegDtDesc(Profile seller, Profile buyer,
        Pageable pageable);

    List<NftHistory> findByBuyer_AddressAndEventTypeOrderByRegDtDesc(
        String address, String eventType, Pageable pageable);

}