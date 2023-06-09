package onthemars.back.nft.repository;

import java.util.List;
import java.util.Optional;

import onthemars.back.nft.entity.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.web3j.abi.datatypes.primitive.Char;

@Repository
public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long> {

    Optional<Transaction> findByIdAndIsBurnIsFalse(Long id);

    List<Transaction> findByIsBurnFalseAndPriceBetweenOrderByPriceAsc(    // search, sort = "1"
        Double priceStart, Double priceEnd);

    List<Transaction> findByIsBurnFalseAndPriceBetweenOrderByPriceDesc(    // search, sort = "2"
        Double priceStart, Double priceEnd);

    List<Transaction> findByIsBurnFalseAndPriceBetweenOrderByUpdDtDesc(    // search, sort = "3"
        Double priceStart, Double priceEnd);

    @Query("SELECT e FROM Transaction e WHERE SUBSTRING(e.dna, 3, 1) = :codeNum AND e.price BETWEEN :priceStart AND :priceEnd AND e.isBurn = false ORDER BY e.price ASC")
    List<Transaction> findByCropTypeWithFilterAndSort1(    // filter, sort = "1"
        @Param("priceStart") Double priceStart, @Param("priceEnd") Double priceEnd, @Param("codeNum") String codeNum);

    @Query("SELECT e FROM Transaction e WHERE SUBSTRING(e.dna, 3, 1) = :codeNum AND e.price BETWEEN :priceStart AND :priceEnd AND e.isBurn = false ORDER BY e.price DESC")
    List<Transaction> findByCropTypeWithFilterAndSort2(    // filter, sort = "2"
        @Param("priceStart") Double priceStart, @Param("priceEnd") Double priceEnd, @Param("codeNum") String codeNum);

    @Query("SELECT e FROM Transaction e WHERE SUBSTRING(e.dna, 3, 1) = :codeNum AND e.price BETWEEN :priceStart AND :priceEnd AND e.isBurn = false ORDER BY e.updDt DESC")
    List<Transaction> findByCropTypeWithFilterAndSort3(    // filter, sort = "3"
        @Param("priceStart") Double priceStart, @Param("priceEnd") Double priceEnd, @Param("codeNum") String codeNum);

    Optional<Transaction> findByDna(String dna);

    List<Transaction> findByDnaStartsWithOrDnaStartsWithAndIsBurnOrderByRegDtDesc(String dna, String dna1, Boolean isBurn);

    List<Transaction> findByDnaStartsWithOrDnaStartsWithAndIsBurn(String dna, String dna1,
        Boolean isBurn);

    @Query("select t from Transaction t " +
            "where (t.dna like concat(?1, '%') or t.dna like concat(?2, '%')) and t.isSale = ?3 and t.isBurn =?4")
    List<Transaction> findByDnaStartsWithAndDnaStartsWithAndIsSaleAndIsBurn(String dna1, String dna2, Boolean isSale, Boolean isBurn);

    List<Transaction> findByMember_AddressAndIsBurnOrderByRegDtDesc(String address, Boolean isBurn, Pageable pageable);

    List<Transaction> findByMember_AddressAndDnaStartsWithAndIsSaleIsFalseAndIsBurnIsFalseOrderByUpdDtDesc(
            String address, String dna);

    Optional<Transaction> findByTokenId(Long tokenId);


}
