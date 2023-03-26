package onthemars.back.nft.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import onthemars.back.code.app.MyCode;
import onthemars.back.code.app.MyCropCode;
import onthemars.back.code.repository.CodeRepository;
import onthemars.back.code.service.CodeService;
import onthemars.back.nft.dto.response.NftAttributesDto;
import onthemars.back.nft.dto.response.NftAttributesDto.Attribute;
import onthemars.back.nft.dto.response.NftCropTypeDetailResDto;
import onthemars.back.nft.dto.response.NftDetailResDto;
import onthemars.back.nft.dto.response.NftItemResDto;
import onthemars.back.nft.entity.Nft;
import onthemars.back.nft.entity.NftHistory;
import onthemars.back.nft.entity.NftT2;
import onthemars.back.nft.entity.Transaction;
import onthemars.back.nft.repository.FavoriteRepository;
import onthemars.back.nft.repository.NftHistoryRepository;
import onthemars.back.nft.repository.NftRepository;
import onthemars.back.nft.repository.NftT2Repository;
import onthemars.back.nft.repository.TransactionRepository;
import onthemars.back.nft.repository.ViewsRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class NftService {

    private final CodeService codeService;
    private final CodeRepository codeRepository;
    private final NftRepository nftRepository;
    private final NftT2Repository nftT2Repository;
    private final FavoriteRepository favoriteRepository;
    private final ViewsRepository viewsRepository;
    private final TransactionRepository transactionRepository;
    private final NftHistoryRepository nftHistoryRepository;

    public NftDetailResDto findNftDetail(String nftId) {
        final Transaction transaction = findTransactionByNftId(nftId);
        final Nft nft = transaction.getNft();
        final NftT2 nftT2 = nftT2Repository
            .findById(nftId)
            .orElse(null);
        final LocalDateTime lastUpdate = nftHistoryRepository
            .findFirstByOrderByRegDtDesc()
            .getRegDt();
        final String cropParent = codeService
            .getCode(MyCropCode.class, nft.getType())
            .getPlural();

        final List<Attribute> attributes = null != nftT2
            ? NftAttributesDto.of(
            nft.getTier().toString(),
            codeService.getCode(MyCode.class, nft.getBg()).getName(),
            codeService.getCode(MyCode.class, nftT2.getEyes()).getName(),
            codeService.getCode(MyCode.class, nftT2.getHeadgear()).getName(),
            codeService.getCode(MyCode.class, nftT2.getMouth()).getName()
        )
            : NftAttributesDto.of(
                nft.getTier().toString(),
                codeService.getCode(MyCode.class, nft.getBg()).getName()
            );

        return NftDetailResDto.of(transaction, attributes, lastUpdate, cropParent);

    } //TODO Exception

    public List<NftHistory> findNftActivites(String nftId, Pageable pageable) {
        return nftHistoryRepository.findByNft_AddressOrderByRegDtDesc(nftId, pageable);
    }

    public List<NftItemResDto> findNfts(String cropType) {

        final List<Transaction> transactionList = transactionRepository
            .findByNft_TypeOrderByRegDtDesc(cropType);
        final List<NftItemResDto> nfts = new ArrayList<>();

        for (Transaction transaction : transactionList) {
            final Nft nft = transaction.getNft();
            final String nftName = String.format("# %s", nft.getTokenId());
            final Double price = transaction.getActivated()
                ? transaction.getPrice()
                : -1;
            final Double lastSalePrice = findLastSalesPrice(nft.getAddress());

            final NftItemResDto nftItem = NftItemResDto.of(
                transaction, nftName, price, lastSalePrice
            );

            nfts.add(nftItem);
        }

        return nfts;
    }

    public NftCropTypeDetailResDto findCropTypeDetail(String cropType) {
        final MyCropCode myCropCode = codeService.getCode(MyCropCode.class, cropType);
        final Integer totalVolume = findTotalVolume(cropType);
        final Double floorPrice = findFloorPrice(cropType);
        final Integer listed = findPercentageOfListed(cropType);
        final Integer mintedCnt = findNumOfMinted(cropType);

        return NftCropTypeDetailResDto.of(
            myCropCode, totalVolume, floorPrice, listed, mintedCnt
        );
    }

    private Double findLastSalesPrice(String nftId) {
        final NftHistory nftHistory = nftHistoryRepository
            .findFirstByNft_AddressAndEventTypeOrderByRegDtDesc(nftId, "TRC03")
            .orElse(null); //TODO 하드코딩하면 나중에 공통코드 변경 시 반영이 안 되는 문제

        return null != nftHistory
            ? nftHistory.getPrice()
            : -1;
    }

    private Transaction findTransactionByNftId(String address) {
        return transactionRepository.findByNft_Address(address);    //TODO
    }

    private Integer findTotalVolume(String cropType) {
        final List<NftHistory> nftSaleHistories = nftHistoryRepository
            .findByNft_TypeAndEventType(cropType, "TRC03");
        Double doubleTotalVolume = 0.0;

        for (NftHistory nftSaleHistory : nftSaleHistories) {
            doubleTotalVolume += nftSaleHistory.getPrice();
        }

        return doubleTotalVolume.intValue();
    }

    private Double findFloorPrice(String cropType) {
        final NftHistory nftHistory = nftHistoryRepository
            .findFirstByNft_TypeAndEventTypeOrderByPriceDesc(cropType, "TRC03")
            .orElse(null);

        return null != nftHistory
            ? nftHistory.getPrice()
            : -1;
    }

    private Integer findPercentageOfListed(String cropType) {
        final Integer numOfListed = transactionRepository
            .findByNft_TypeAndActivated(cropType, true)
            .size();
        final Integer numOfMinted = findNumOfMinted(cropType);
        return numOfListed / numOfMinted;
    }

    private Integer findNumOfMinted(String cropType) {
        return nftRepository
            .findByTypeAndActivated(cropType, true)
            .size();
    }
}
