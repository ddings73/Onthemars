package onthemars.back.nft.repository;

import java.util.List;
import onthemars.back.nft.entity.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long> {

    List<Transaction> findByDnaStartsWithOrDnaStartsWithAndIsBurn(String dna, String dna1,
        Boolean isBurn);

    List<Transaction> findByDnaStartsWithOrDnaStartsWithAndIsSale(String dna, String dna1,
        Boolean isSale);

    List<Transaction> findByMember_AddressOrderByRegDtDesc(String address, Pageable pageable);

    List<Transaction> findByMember_AddressAndDnaStartsWithOrderByRegDtAsc(String address,
        String dna, Pageable pageable);

}
