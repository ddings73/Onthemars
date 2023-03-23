package onthemars.back.nft.service;

import lombok.RequiredArgsConstructor;
import onthemars.back.nft.dto.response.NftDetailResDto;
import onthemars.back.nft.entity.Nft;
import onthemars.back.nft.entity.NftHistory;
import onthemars.back.nft.entity.Transaction;
import onthemars.back.nft.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class NftService {

    private final NftRepository nftRepository;
    private final NftT2Repository nftT2Repository;
    private final FavoriteRepository favoriteRepository;
    private final ViewsRepository viewsRepository;
    private final TransactionRepository transactionRepository;
    private final NftHistoryRepository nftHistoryRepository;

    public NftDetailResDto findNftDetail(String nftId) {
        final Nft nft = findNftById(nftId);
        final String ownerNickname = findOwnerNickname(nftId);
        final LocalDateTime lastUpdate = nftHistoryRepository.findFirstByOrderByRegDtDesc().getRegDt();
        final Transaction transaction = findTransactionByNftId(nftId);

        return NftDetailResDto.from(nft, ownerNickname, lastUpdate, transaction);
    }    //TODO 엔티티 최소로 불러오는거 말고도 할 거 있었는데 .. ? 뭐드라


    public Nft findNftById(String nftId) {
        return nftRepository.findById(nftId).orElseThrow();    //TODO Exception 처리?
    }

    public String findOwnerNickname(String nftId) {
        return nftRepository
                .findById(nftId)
                .orElseThrow()
                .getMember()
                .getNickname();
    }

    public Transaction findTransactionByNftId(String address) {
        return transactionRepository.findByNft_Address(address);    //TODO
    }

}
