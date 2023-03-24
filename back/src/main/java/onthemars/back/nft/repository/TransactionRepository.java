package onthemars.back.nft.repository;

import onthemars.back.nft.entity.Transaction;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long> {

    Transaction findByNft_Address(String address);

}
