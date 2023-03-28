package onthemars.back.nft.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import onthemars.back.code.app.MyCode;
import onthemars.back.code.app.MyCropCode;
import onthemars.back.code.service.CodeService;
import onthemars.back.exception.UserNotFoundException;
import onthemars.back.nft.dto.response.ActivityItemResDto;
import onthemars.back.nft.dto.response.AlbumItemResDto;
import onthemars.back.nft.dto.response.AttributesDto;
import onthemars.back.nft.dto.response.AttributesDto.Attribute;
import onthemars.back.nft.dto.response.CombinationItemResDto;
import onthemars.back.nft.dto.response.CropTypeDetailResDto;
import onthemars.back.nft.dto.response.DetailResDto;
import onthemars.back.nft.dto.response.UserActivityItemResDto;
import onthemars.back.nft.entity.Favorite;
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
import onthemars.back.user.domain.Member;
import onthemars.back.user.domain.Profile;
import onthemars.back.user.repository.MemberRepository;
import onthemars.back.user.service.AuthService;
import onthemars.back.user.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class NftService {

    private final CodeService codeService;
    private final UserService userService;
    private final AuthService authService;
    private final NftRepository nftRepository;
    private final NftT2Repository nftT2Repository;
    private final FavoriteRepository favoriteRepository;
    private final ViewsRepository viewsRepository;
    private final TransactionRepository transactionRepository;
    private final NftHistoryRepository nftHistoryRepository;
    private final MemberRepository memberRepository;

    public DetailResDto findNftDetail(String nftId) {
        final Transaction transaction = findTransactionByNftId(nftId);
        final Nft nft = transaction.getNft();
        final NftT2 nftT2 = nftT2Repository
            .findById(nftId)
            .orElse(null);
        final LocalDateTime lastUpdate = nftHistoryRepository
            .findFirstByOrderByRegDtDesc()
            .getRegDt();

        final MyCropCode myCropCode = codeService.getCode(MyCropCode.class, nft.getType());
        final String cropParent = myCropCode.getPlural();
        final String nftType = myCropCode.getName();
        final String nftName =
            nftType.charAt(0) + nftType.substring(1).toLowerCase() + " #" + nft.getTokenId();

        final List<Attribute> attributes = null != nftT2
            ? AttributesDto.of(
            nft.getTier().toString(),
            codeService.getCode(MyCode.class, nft.getBg()).getName(),
            codeService.getCode(MyCode.class, nftT2.getEyes()).getName(),
            codeService.getCode(MyCode.class, nftT2.getHeadgear()).getName(),
            codeService.getCode(MyCode.class, nftT2.getMouth()).getName()
        )
            : AttributesDto.of(
                nft.getTier().toString(),
                codeService.getCode(MyCode.class, nft.getBg()).getName()
            );

        return DetailResDto.of(transaction, attributes, lastUpdate, cropParent, nftName);

    }

    public List<ActivityItemResDto> findNftActivitesDto(String nftAddress, Pageable pageable) {
        final List<NftHistory> nftHistoryList = findNftActivites(nftAddress, pageable);
        final List<ActivityItemResDto> activities = new ArrayList<>();

        for (NftHistory history : nftHistoryList) {
            final String eventCap = codeService
                .getCode(MyCode.class, history.getEventType())
                .getName();
            final String event = eventCap.charAt(0) + eventCap.substring(1).toLowerCase();
            final ActivityItemResDto activity = ActivityItemResDto.of(history, event);
            activities.add(activity);
        }
        return activities;
    }

    private List<NftHistory> findNftActivites(String nftId, Pageable pageable) {
        return nftHistoryRepository.findByNft_AddressOrderByRegDtDesc(nftId, pageable);
    }

    public List<AlbumItemResDto> findNfts(String cropType) {

        final List<Transaction> transactionList = transactionRepository
            .findByNft_TypeOrderByRegDtDesc(cropType);
        final List<AlbumItemResDto> nfts = new ArrayList<>();

        for (Transaction transaction : transactionList) {
            final Nft nft = transaction.getNft();
            final String type = nft.getType();
            final String nftTokenId = nft.getTokenId();

            final String nftType = codeService.getCode(MyCropCode.class, type).getName();
            final String nftName =
                nftType.charAt(0) + nftType.substring(1).toLowerCase() + " #" + nftTokenId;
            final Double price = transaction.getActivated()
                ? transaction.getPrice()
                : -1;
            final Double lastSalePrice = findLastSalesPrice(nft.getAddress());

            final AlbumItemResDto nftItem = AlbumItemResDto.of(
                transaction, nftName, price, lastSalePrice
            );

            nfts.add(nftItem);
        }

        return nfts;
    }

    public CropTypeDetailResDto findCropTypeDetail(String cropType) {
        final MyCropCode myCropCode = codeService.getCode(MyCropCode.class, cropType);
        final String cropParent = makePascalCase(myCropCode.getPlural());
        final Integer totalVolume = findTotalVolumeByCropType(cropType);
        final Double floorPrice = findFloorPrice(cropType);
        final Integer listed = findPercentageOfListed(cropType);
        final Integer mintedCnt = findNumOfMinted(cropType);

        return CropTypeDetailResDto.of(
            myCropCode, cropParent, totalVolume, floorPrice, listed, mintedCnt
        );
    }

    public List<AlbumItemResDto> findCollectedNfts(String userAddress, Pageable pageable) {
        final List<Transaction> transactions = transactionRepository
            .findByNft_Member_AddressOrderByRegDtDesc(userAddress, pageable);
        final List<AlbumItemResDto> dtos = new ArrayList<>();    //TODO private method로 중복 부분 빼기

        for (Transaction transaction : transactions) {
            final Nft nft = transaction.getNft();
            final String type = nft.getType();
            final String nftTokenId = nft.getTokenId();

            final String nftType = codeService.getCode(MyCropCode.class, type).getName();
            final String nftName =
                nftType.charAt(0) + nftType.substring(1).toLowerCase() + " #" + nftTokenId;
            final Double price = transaction.getActivated()
                ? transaction.getPrice()
                : -1;
            final Double lastSalePrice = findLastSalesPrice(nft.getAddress());

            final AlbumItemResDto dto = AlbumItemResDto.of(
                transaction, nftName, price, lastSalePrice
            );

            dtos.add(dto);
        }

        return dtos;
    }

    public List<AlbumItemResDto> findMintedNfts(String userAddress, Pageable pageable) {
        final List<NftHistory> histories = nftHistoryRepository
            .findByBuyer_AddressAndEventTypeOrderByRegDtDesc(userAddress, "TRC01", pageable);
        final List<AlbumItemResDto> dtos = new ArrayList<>();    //TODO private method로 중복 부분 빼기

        for (NftHistory history : histories) {
            final Nft nft = history.getNft();
            final String nftTokenId = nft.getTokenId();

            final Transaction transaction = transactionRepository
                .findByNft_Address(nft.getAddress());
            final String nftType = codeService.getCode(MyCropCode.class, nft.getType()).getName();
            final String nftName =
                nftType.charAt(0) + nftType.substring(1).toLowerCase() + " #" + nftTokenId;
            final Double price = transaction.getActivated()
                ? transaction.getPrice()
                : -1;
            final Double lastSalePrice = findLastSalesPrice(nft.getAddress());

            final AlbumItemResDto dto = AlbumItemResDto.of(
                transaction, nftName, price, lastSalePrice
            );

            dtos.add(dto);
        }

        return dtos;
    }

    public List<UserActivityItemResDto> findNftActivitesByUser(String userAddress,
        Pageable pageable) {
        final Profile user = userService.findProfile(userAddress);
        final List<NftHistory> nftHistoryList = nftHistoryRepository
            .findBySellerOrBuyerOrderByRegDtDesc(user, user, pageable);
        final List<UserActivityItemResDto> activities = new ArrayList<>();    //TODO 중복 제거, eventType, cropParent, nftName 첫 글자 대문자 만드는 private 메서드 추가

        for (NftHistory history : nftHistoryList) {
            final Nft nft = history.getNft();
            final MyCropCode myCropCode = codeService.getCode(MyCropCode.class, nft.getType());
            final String cropParent = makePascalCase(myCropCode.getPlural());
            final String nftType = makePascalCase(myCropCode.getName());
            final String nftName = nftType + " #" + nft.getTokenId();

            final String eventCap = codeService
                .getCode(MyCode.class, history.getEventType())
                .getName();
            final String event = eventCap.charAt(0) + eventCap.substring(1).toLowerCase();
            final UserActivityItemResDto activity = UserActivityItemResDto.of(history, event,
                cropParent, nftName);
            activities.add(activity);
        }
        return activities;
    }

    public List<CombinationItemResDto> findNftsForCombination(Pageable pageable) {
        final String userAddress = authService.findCurrentUserAddress();
        final List<Nft> nfts = nftRepository
            .findByMember_AddressAndTierOrderByAddressAsc(userAddress, 1, pageable);
        final List<CombinationItemResDto> dtos = new ArrayList<>();

        for (Nft nft : nfts) {
            final CombinationItemResDto dto = CombinationItemResDto.of(nft);
            dtos.add(dto);
        }

        return dtos;
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

    private Integer findTotalVolumeByCropType(String cropType) {
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
        return 0 != numOfMinted
            ? numOfListed / numOfMinted
            : -1;
    }

    private Integer findNumOfMinted(String cropType) {
        return nftRepository
            .findByTypeAndActivated(cropType, true)
            .size();
    }

    private String makePascalCase(String attr) {
        return attr.charAt(0) + attr.substring(1).toLowerCase();
    }

    public void updateFavoriteActivated(String nftAddress) {
        final String userAddress = authService.findCurrentUserAddress();
        final Member member = memberRepository
            .findById(userAddress)
            .orElseThrow(UserNotFoundException::new);
        final Transaction transaction = transactionRepository
            .findByNft_Address(nftAddress);

        Favorite favorite = favoriteRepository
            .findByMember_AddressAndTransaction_Id(userAddress, transaction.getId())
            .orElseGet(() -> favoriteRepository.save(new Favorite(member, transaction)));

        favorite.updateActivated();
    }

    public List<AlbumItemResDto> findFavoritedNfts(String userAddress, Pageable pageable) {
        final List<Favorite> favorites = favoriteRepository
            .findByMember_AddressAndActivated(userAddress, true, pageable);
        final List<AlbumItemResDto> dtos = new ArrayList<>();

        for (Favorite favorite : favorites) {
            final Transaction transaction = transactionRepository
                .findById(favorite.getTransaction().getId())
                .orElseGet(null);    //TODO 조합 후 nft가 삭제되는 경우 등에 대처하는 로직 필요 on delete cascade 설정해두긴함

            final Nft nft = transaction.getNft();
            final String type = nft.getType();
            final String nftTokenId = nft.getTokenId();

            final String nftType = codeService.getCode(MyCropCode.class, type).getName();
            final String nftName =
                nftType.charAt(0) + nftType.substring(1).toLowerCase() + " #" + nftTokenId;
            final Double price = transaction.getActivated()
                ? transaction.getPrice()
                : -1;
            final Double lastSalePrice = findLastSalesPrice(nft.getAddress());

            final AlbumItemResDto dto = AlbumItemResDto.of(
                transaction, nftName, price, lastSalePrice
            );

            dtos.add(dto);
        }

        return dtos;
    }
}
