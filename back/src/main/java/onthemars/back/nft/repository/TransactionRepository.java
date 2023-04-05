package onthemars.back.nft.repository;

import java.util.List;
import java.util.Optional;

import onthemars.back.nft.entity.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long> {

    List<Transaction> findByIsBurnFalseAndPriceBetweenOrderByPriceAsc(    // search, sort = "1"
        Double priceStart, Double priceEnd);

    List<Transaction> findByIsBurnFalseAndPriceBetweenOrderByPriceDesc(    // search, sort = "2"
        Double priceStart, Double priceEnd);

    List<Transaction> findByIsBurnFalseAndPriceBetweenOrderByUpdDtDesc(    // search, sort = "3"
        Double priceStart, Double priceEnd);

    List<Transaction> findByIsBurnFalseAndPriceBetweenAndDnaStartsWithOrDnaStartsWithOrderByPriceAsc(    // filter, sort = "1"
        Double priceStart, Double priceEnd, String dna1, String dna2);

    List<Transaction> findByIsBurnFalseAndPriceBetweenAndDnaStartsWithOrDnaStartsWithOrderByPriceDesc(    // filter, sort = "2"
        Double priceStart, Double priceEnd, String dna1, String dna2);

    List<Transaction> findByIsBurnFalseAndPriceBetweenAndDnaStartsWithOrDnaStartsWithOrderByUpdDtDesc(    // filter, sort = "3"
        Double priceStart, Double priceEnd, String dna1, String dna2);

    Optional<Transaction> findByDna(String dna);

    List<Transaction> findByDnaStartsWithOrDnaStartsWithAndIsBurnOrderByRegDtDesc(String dna, String dna1, Boolean isBurn);

    List<Transaction> findByDnaStartsWithOrDnaStartsWithAndIsBurn(String dna, String dna1,
        Boolean isBurn);

    @Query("select t from Transaction t " +
            "where (t.dna like concat(?1, '%') or t.dna like concat(?2, '%')) and t.isSale = ?3 and t.isBurn =?4")
    List<Transaction> findByDnaStartsWithAndDnaStartsWithAndIsSaleAndIsBurn(String dna1, String dna2, Boolean isSale, Boolean isBurn);

    List<Transaction> findByMember_AddressAndIsBurnOrderByRegDtDesc(String address, Boolean isBurn, Pageable pageable);

    List<Transaction> findByMember_AddressAndDnaStartsWithAndIsSaleAndIsBurnOrderByRegDtAsc(
            String address, String dna, Boolean isSale, Boolean isBurn);

    List<Transaction> findByMember_AddressAndIsSaleAndIsBurnOrderByRegDtAsc(
        String address, Boolean isSale, Boolean isBurn);

    Optional<Transaction> findByTokenId(Long tokenId);

}
