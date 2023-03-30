package onthemars.back.nft.repository;

import java.util.List;
import onthemars.back.nft.entity.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long> {
    List<Transaction> findByDnaStartsWithOrDnaStartsWithOrderByRegDtDesc(String dna, String dna1);

    List<Transaction> findByDnaStartsWithOrDnaStartsWithAndIsBurn(String dna, String dna1,
        Boolean isBurn);

    @Query("select t from Transaction t " +
            "where (t.dna like concat(?1, '%') or t.dna like concat(?2, '%')) and t.isSale = ?3")
    List<Transaction> findByDnaStartsWithAndDnaStartsWithAndIsSale(String dna1, String dna2, Boolean isSale);

    List<Transaction> findByMember_AddressOrderByRegDtDesc(String address, Pageable pageable);

    List<Transaction> findByMember_AddressAndDnaStartsWithOrderByRegDtAsc(String address,
        String dna);

}
