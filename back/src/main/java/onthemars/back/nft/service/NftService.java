package onthemars.back.nft.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onthemars.back.code.app.MyCode;
import onthemars.back.code.app.MyCropCode;
import onthemars.back.code.service.CodeService;
import onthemars.back.exception.UserNotFoundException;
import onthemars.back.nft.dto.response.*;
import onthemars.back.nft.dto.response.AttributesDto.Attribute;
import onthemars.back.nft.entity.Favorite;
import onthemars.back.nft.entity.NftHistory;
import onthemars.back.nft.entity.Transaction;
import onthemars.back.nft.repository.FavoriteRepository;
import onthemars.back.nft.repository.NftHistoryRepository;
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

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class NftService {

    private final CodeService codeService;
    private final UserService userService;
    private final AuthService authService;
    private final FavoriteRepository favoriteRepository;
    private final ViewsRepository viewsRepository;
    private final TransactionRepository transactionRepository;
    private final NftHistoryRepository nftHistoryRepository;
    private final MemberRepository memberRepository;

    public DetailResDto findNftDetail(Long transactionId) {
        final Transaction transaction = transactionRepository
            .findById(transactionId)
            .orElseThrow(); //TODO 예외 처리

        final LocalDateTime lastUpdate = nftHistoryRepository
            .findFirstByTransaction_IdOrderByRegDtDesc(transactionId)
            .orElseGet(null)
            .getRegDt();

        final List<String> attributes = decodeDna(transaction.getDna());
        final MyCropCode myCropCode = codeService.getCode(MyCropCode.class, attributes.get(0));
        final String cropParent = capitalizeFirst(myCropCode.getPlural());
        final String nftType = myCropCode.getName();
        final String nftName = capitalizeFirst(nftType) + " #" + transaction.getTokenId();

        final List<Attribute> attributeList = (5 == attributes.size())
            ? AttributesDto.of(
            String.valueOf(transaction.getDna().charAt(0)),
            capitalizeFirst(codeService.getCode(MyCode.class, attributes.get(1)).getName()),
            capitalizeFirst(codeService.getCode(MyCode.class, attributes.get(2)).getName()),
            capitalizeFirst(codeService.getCode(MyCode.class, attributes.get(3)).getName()),
            capitalizeFirst(codeService.getCode(MyCode.class, attributes.get(4)).getName())
        )
            : AttributesDto.of(
                String.valueOf(transaction.getDna().charAt(0)),
                capitalizeFirst(codeService.getCode(MyCode.class, attributes.get(1)).getName())
            );

        final String userAddress = authService.findCurrentOrAnonymousUser();

        final Boolean isOwner = (transaction.getMember().getAddress().equals(userAddress));

        final Favorite favorite = favoriteRepository
            .findByMember_AddressAndTransaction_Id(userAddress, transactionId)
            .orElse(null);
        final Boolean isFavorite = null != favorite
            ? favorite.getActivated()
            : false;

        return DetailResDto.of(transaction, attributeList, lastUpdate, cropParent, nftName, isOwner, isFavorite);
    }

    public List<ActivityItemResDto> findNftActivitesDto(Long transactionId, Pageable pageable) {
        final List<NftHistory> nftHistoryList = nftHistoryRepository
            .findByTransaction_IdOrderByRegDtDesc(transactionId, pageable);
        final List<ActivityItemResDto> activities = new ArrayList<>();

        for (NftHistory history : nftHistoryList) {
            final String event = capitalizeFirst(codeService
                .getCode(MyCode.class, history.getEventType())
                .getName());
            final ActivityItemResDto activity = ActivityItemResDto.of(history, event);
            activities.add(activity);
        }
        return activities;
    }

    public List<AlbumItemResDto> findNftsByCropType(String cropType) {
        final String codeNum = cropType.substring(3);
        final List<Transaction> transactionList = transactionRepository
                .findByDnaStartsWithOrDnaStartsWithOrderByRegDtDesc("1" + codeNum, "2" + codeNum);
        final List<AlbumItemResDto> dtos = new ArrayList<>();

        for (Transaction transaction : transactionList) {
            final AlbumItemResDto nftItem = AlbumItemResDto.of(transaction);
            dtos.add(nftItem);
        }
        return dtos;
    }

    public CropTypeDetailResDto findCropTypeDetail(String cropType) {
        final MyCropCode myCropCode = codeService.getCode(MyCropCode.class, cropType);
        final String cropParent = capitalizeFirst(myCropCode.getPlural());
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
            .findByMember_AddressOrderByRegDtDesc(userAddress, pageable);

        final List<AlbumItemResDto> dtos = new ArrayList<>();
        for (Transaction transaction : transactions) {
            final AlbumItemResDto dto = AlbumItemResDto.of(transaction);
            dtos.add(dto);
        }

        return dtos;
    }

    public List<AlbumItemResDto> findMintedNfts(String userAddress, Pageable pageable) {
        final List<NftHistory> histories = nftHistoryRepository
            .findByBuyer_AddressAndEventTypeOrderByRegDtDesc(userAddress, "TRC01", pageable);
        final List<AlbumItemResDto> dtos = new ArrayList<>();    //TODO private method로 중복 부분 빼기

        for (NftHistory history : histories) {
            final Transaction transaction = history.getTransaction();
            final AlbumItemResDto dto = AlbumItemResDto.of(transaction);
            dtos.add(dto);
        }

        return dtos;
    }

    public List<CombinationItemResDto> findAllNftsForCombination(Pageable pageable) {
        final String userAddress = authService.findCurrentUserAddress();
        final List<Transaction> transactions = transactionRepository
            .findByMember_AddressAndDnaStartsWithOrderByRegDtAsc(userAddress, "1", pageable);
        final List<CombinationItemResDto> dtos = new ArrayList<>();

        for (Transaction transaction : transactions) {
            final List<String> attributes = decodeDna(transaction.getDna());
            final String cropName = capitalizeFirst(codeService
                    .getCode(MyCropCode.class, attributes.get(0))
                    .getName());

            final CombinationItemResDto dto = CombinationItemResDto.of(transaction, cropName);
            dtos.add(dto);
        }
        return dtos;
    }

    public List<CombinationItemResDto> findNftsForCombinationByCropType(String cropType, Pageable pageable) {
        final String userAddress = authService.findCurrentUserAddress();
        final List<Transaction> transactions = transactionRepository
            .findByMember_AddressAndDnaStartsWithOrderByRegDtAsc(userAddress, "1" + cropType.substring(3), pageable);
        final List<CombinationItemResDto> dtos = new ArrayList<>();

        for (Transaction transaction : transactions) {
            final List<String> attributes = decodeDna(transaction.getDna());
            final String cropName = capitalizeFirst(codeService
                .getCode(MyCropCode.class, attributes.get(0))
                .getName());
            final CombinationItemResDto dto = CombinationItemResDto.of(transaction, cropName);
            dtos.add(dto);
        }
        return dtos;
    }

    public List<UserActivityItemResDto> findNftActivitesByUser(String userAddress,
        Pageable pageable) {
        final Profile user = userService.findProfile(userAddress);
        final List<NftHistory> nftHistoryList = nftHistoryRepository
            .findBySellerOrBuyerOrderByRegDtDesc(user, user, pageable);
        final List<UserActivityItemResDto> activities = new ArrayList<>();

        for (NftHistory history : nftHistoryList) {
            final Transaction transaction = history.getTransaction();
            final List<String> attributes = decodeDna(transaction.getDna());

            final MyCropCode myCropCode = codeService.getCode(MyCropCode.class, attributes.get(0));
            final String cropParent = capitalizeFirst(myCropCode.getPlural());
            final String nftType = capitalizeFirst(myCropCode.getName());
            final String nftName = nftType + " #" + transaction.getTokenId();
            final String event = capitalizeFirst(codeService
                .getCode(MyCode.class, history.getEventType())
                .getName());

            final UserActivityItemResDto activity = UserActivityItemResDto
                .of(history, event, cropParent, nftName);
            activities.add(activity);
        }
        return activities;
    }

    public List<AlbumItemResDto> findFavoritedNfts(String userAddress, Pageable pageable) {
        final List<Favorite> favorites = favoriteRepository
            .findByMember_AddressAndActivated(userAddress, true, pageable);
        final List<AlbumItemResDto> dtos = new ArrayList<>();

        for (Favorite favorite : favorites) {
            final Transaction transaction = transactionRepository
                .findById(favorite.getTransaction().getId())
                .orElseGet(
                    null);    //TODO 조합 후 nft가 삭제되는 경우 등에 대처하는 로직 필요 DB엔 on delete cascade 설정해두긴함

            final AlbumItemResDto dto = AlbumItemResDto.of(transaction);

            dtos.add(dto);
        }

        return dtos;
    }


    public void updateFavoriteActivated(Long transactionId) {
        final String userAddress = authService.findCurrentUserAddress();
        final Member member = memberRepository
            .findById(userAddress)
            .orElseThrow(UserNotFoundException::new);
        final Transaction transaction = transactionRepository
            .findById(transactionId).orElseThrow(); //TODO 예외 처리

        Favorite favorite = favoriteRepository
            .findByMember_AddressAndTransaction_Id(userAddress, transaction.getId())
            .orElseGet(() -> favoriteRepository.save(new Favorite(member, transaction)));

        favorite.updateActivated();
    }

    public List<GraphItemResDto> findGraphItems(Long transactionId) {
        final List<NftHistory> hitories = nftHistoryRepository
            .findByTransaction_IdAndEventTypeOrderByRegDtAsc(transactionId, "TRC03");
        final List<GraphItemResDto> dtos = new ArrayList<>();

        for (NftHistory history : hitories) {
            final GraphItemResDto dto = GraphItemResDto.of(history);
            dtos.add(dto);
        }

        return dtos;
    }

    private Double findLastSalesPrice(Long transactionId) {
        final NftHistory nftHistory = nftHistoryRepository
            .findFirstByTransaction_IdAndEventTypeOrderByRegDtDesc(transactionId, "TRC03")
            .orElse(null);

        return null != nftHistory
            ? nftHistory.getPrice()
            : -1;
    }

    private Integer findTotalVolumeByCropType(String cropType) {
        final String codeNum = cropType.substring(3);
        final List<NftHistory> nftSaleHistories = nftHistoryRepository
            .findByTransaction_DnaStartsWithOrTransaction_DnaStartsWith(1 + codeNum, 2 + codeNum);
        Double doubleTotalVolume = 0.0;

        for (NftHistory nftSaleHistory : nftSaleHistories) {
            if (nftSaleHistory.getEventType().equals("TRC03")) {
                doubleTotalVolume += nftSaleHistory.getPrice();
            }
        }
        return doubleTotalVolume.intValue();
    }

    private Double findFloorPrice(String cropType) {
        final String codeNum = cropType.substring(3);
        final NftHistory nftHistory = nftHistoryRepository
            .findFirstByTransaction_DnaStartsWithOrTransaction_DnaStartsWithAndEventTypeOrderByTransaction_PriceDesc(
                "1" + codeNum,
                "2" + codeNum,
                "TRC03"
            )
            .orElse(null);

        return null != nftHistory
            ? nftHistory.getPrice()
            : -1;
    }

    private Integer findPercentageOfListed(String cropType) {
        final String codeNum = cropType.substring(3);
        final int numOfListed = transactionRepository
            .findByDnaStartsWithAndDnaStartsWithAndIsSale(
                1 + codeNum,
                2 + codeNum,
                true
            )
            .size();
        final int numOfMinted = findNumOfMinted(cropType);
        final double percentageOfListed = 0 != numOfMinted
            ? (double) numOfListed / numOfMinted * 100
            : 0;
        return Math.toIntExact(Math.round(percentageOfListed));
    }

    private Integer findNumOfMinted(String cropType) {
        final String codeNum = cropType.substring(3);
        return transactionRepository
            .findByDnaStartsWithOrDnaStartsWithAndIsBurn(
                1 + codeNum,
                2 + codeNum,
                false
            )
            .size();
    }

    private String capitalizeFirst(String attr) {
        return attr.charAt(0) + attr.substring(1).toLowerCase();
    }

    private List<String> decodeDna(String dna) {
        // 티어 + 종류 + 배경색 + 눈 + 입 + 머리
        final String[] attrsArr = {"CRS", "CLR", "EYE", "MOU", "HDG"};

        final List<String> decodedAttrs = new ArrayList<>();
        final int endIdx = '1' == dna.charAt(0) ? 2 : 5;
        for (int i = 0; i < endIdx; i++) {
            final String mod = String.valueOf(
                Integer.parseInt(dna.substring(2 * i + 1, 2 * i + 3)) % 10);
            final String codeNum = mod.equals("0") ? "1" + mod : "0" + mod;
            decodedAttrs.add(attrsArr[i] + codeNum);
        }
        return decodedAttrs;
    }
}
