package onthemars.back.nft.service;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import onthemars.back.nft.entity.Favorite;
import onthemars.back.nft.entity.Nft;
import onthemars.back.nft.entity.NftHistory;
import onthemars.back.nft.entity.Transaction;
import onthemars.back.nft.repository.FavoriteRepository;
import onthemars.back.nft.repository.NftHistoryRepository;
import onthemars.back.nft.repository.NftRepository;
import onthemars.back.nft.repository.NftT2Repository;
import onthemars.back.nft.repository.TransactionRepository;
import onthemars.back.nft.repository.ViewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Service
@RequiredArgsConstructor
public class NftService {

    private final NftRepository nftRepository;
    private final NftT2Repository nftT2Repository;
    private final FavoriteRepository favoriteRepository;
    private final ViewsRepository viewsRepository;
    private final TransactionRepository transactionRepository;
    private final NftHistoryRepository nftHistoryRepository;

    public Nft findNftById(String nftId) {
        return nftRepository.findById(nftId).orElseThrow();    //TODO Exception 처리?
    }

    public String findOwnerNickname() {
        final NftHistory lastMintedOrSales = nftHistoryRepository
            .findFirstByEventTypeOrEventTypeAllIgnoreCaseOrderByRegDtDesc("Minted", "Sales");
        return lastMintedOrSales.getBuyer().getNickname();
    }

    public Double findCurrentPrice() {
        return nftHistoryRepository
            .findFirstByEventTypeOrderByRegDtDesc("List")
            .orElse(null)
            .getPrice();
    }

    public LocalDateTime findLastUpdate() {
        final LocalDateTime now = LocalDateTime.now();
        return nftHistoryRepository
            .findFirstByRegDtBeforeOrderByRegDtDesc(now)
            .getRegDt();
    }

    public Transaction findTransactionByNftId(String address) {
        return transactionRepository.findByNft_Address(address);    //TODO
    }

//    public Favorite findFavoriteByUserId()


}
