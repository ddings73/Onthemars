package onthemars.back.nft.repository;

import java.util.List;
import onthemars.back.nft.entity.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long> {

    Transaction findByNft_Address(String address);

    List<Transaction> findByNft_TypeOrderByRegDtDesc(String type);

    List<Transaction> findByNft_TypeAndActivated(String type, Boolean activated);

    List<Transaction> findByNft_Member_AddressOrderByRegDtDesc(String address, Pageable pageable);

}
