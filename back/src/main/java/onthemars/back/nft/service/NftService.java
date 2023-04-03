package onthemars.back.nft.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onthemars.back.aws.AwsS3Utils;
import onthemars.back.aws.S3Dir;
import onthemars.back.code.app.CodeListItem;
import onthemars.back.code.app.MyCode;
import onthemars.back.code.app.MyCropCode;
import onthemars.back.code.dto.response.CodeListResDto;
import onthemars.back.code.service.CodeService;
import onthemars.back.exception.IllegalSignatureException;
import onthemars.back.exception.UserNotFoundException;
import onthemars.back.nft.dto.request.FilterReqDto;
import onthemars.back.nft.dto.request.FusionReqDto;
import onthemars.back.nft.dto.request.ListingReqDto;
import onthemars.back.nft.dto.request.SearchReqDto;
import onthemars.back.nft.dto.request.TrcListReqDto;
import onthemars.back.nft.dto.response.*;
import onthemars.back.nft.dto.response.AttributesDto.Attribute;
import onthemars.back.nft.entity.Favorite;
import onthemars.back.nft.entity.NftHistory;
import onthemars.back.nft.entity.Transaction;
import onthemars.back.nft.repository.FavoriteRepository;
import onthemars.back.nft.repository.NftHistoryRepository;
import onthemars.back.nft.repository.TransactionRepository;
import onthemars.back.nft.repository.ViewsRepository;
import onthemars.back.notification.app.NotiTitle;
import onthemars.back.notification.service.NotiService;
import onthemars.back.user.domain.Member;
import onthemars.back.user.domain.Profile;
import onthemars.back.user.repository.MemberRepository;
import onthemars.back.user.repository.ProfileRepository;
import onthemars.back.user.service.AuthService;
import onthemars.back.user.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class NftService {

    private final String CONTRACT_ADDRESS = "0x8974Be1FcCE5a14920571AC12D74e67D0B7632Bf";

    private final ProfileRepository profileRepository;
    private final CodeService codeService;
    private final UserService userService;
    private final AuthService authService;
    private final NotiService notiService;
    private final FavoriteRepository favoriteRepository;
    private final ViewsRepository viewsRepository;
    private final TransactionRepository transactionRepository;
    private final NftHistoryRepository nftHistoryRepository;
    private final MemberRepository memberRepository;

    private final AwsS3Utils awsS3Utils;

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

    public List<AlbumItemResDto> findNftsByCropType(String cropType, FilterReqDto filterReqDto) {
        final String codeNum = cropType.substring(3);
        final List<Transaction> transactionList = transactionRepository
                .findByDnaStartsWithOrDnaStartsWithAndIsBurnOrderByRegDtDesc("1" + codeNum, "2" + codeNum, false);
        final List<AlbumItemResDto> dtos = new ArrayList<>();

        final List<String> tierList = filterReqDto.getTier();
        final List<String> bgList = filterReqDto.getBg().stream().map(e -> e.substring(3)).collect(Collectors.toList());
        final List<String> eyesList = filterReqDto.getEyes().stream().map(e -> e.substring(3)).collect(Collectors.toList());
        final List<String> mouthList = filterReqDto.getMouth().stream().map(e -> e.substring(3)).collect(Collectors.toList());
        final List<String> headGearList = filterReqDto.getHeadGear().stream().map(e -> e.substring(3)).collect(Collectors.toList());

        if ((tierList.isEmpty() && bgList.isEmpty() && eyesList.isEmpty() && mouthList.isEmpty() && headGearList.isEmpty())
            || null == filterReqDto
        ) {
            for (Transaction transaction : transactionList) {
                final AlbumItemResDto nftItem = AlbumItemResDto.of(transaction);
                dtos.add(nftItem);
            }
        } else {
            for (Transaction transaction : transactionList) {
                final String dna = transaction.getDna();

                final String tier = dna.substring(0, 1);
                final String bg = dna.substring(3, 5);
                final String eyes = dna.substring(5, 7);
                final String mouth = dna.substring(7, 9);
                final String headGear = dna.substring(9, 11);

                if (tierList.contains(tier)
                    || bgList.contains(bg)
                    || eyesList.contains(eyes)
                    || mouthList.contains(mouth)
                    || headGearList.contains(headGear)
                ) {
                    final AlbumItemResDto nftItem = AlbumItemResDto.of(transaction);
                    dtos.add(nftItem);
                }
            }
        }
        return dtos;
    }

    public CropTypeDetailResDto findCropTypeDetail(String cropType) {
        final MyCode code = codeService.getCode(MyCode.class, cropType);
        final MyCropCode myCropCode = codeService.getCode(MyCropCode.class, cropType);

        final String backImg = myCropCode.getBanner();
        final String cardImg = code.getPath();
        final String cropParent = capitalizeFirst(myCropCode.getPlural());
        final Integer totalVolume = findTotalVolumeByCropType(cropType);
        final Double floorPrice = findFloorPrice(cropType);
        final Integer listed = findPercentageOfListed(cropType);
        final Integer mintedCnt = findNumOfMinted(cropType);

        return CropTypeDetailResDto.of(
                backImg, cardImg, myCropCode, cropParent, totalVolume, floorPrice, listed, mintedCnt
        );
    }

    public List<AlbumItemResDto> findCollectedNfts(String userAddress, Pageable pageable) {
        final List<Transaction> transactions = transactionRepository
                .findByMember_AddressAndIsBurnOrderByRegDtDesc(userAddress, false, pageable);

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

    public List<CombinationItemResDto> findAllNftsForCombination() {
        final String userAddress = authService.findCurrentUserAddress();
        final List<Transaction> transactions = transactionRepository
                .findByMember_AddressAndDnaStartsWithAndIsSaleAndIsBurnOrderByRegDtAsc(userAddress, "1", false, false);
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

    public List<CombinationItemResDto> findNftsForCombinationByCropType(String cropType) {
        final String userAddress = authService.findCurrentUserAddress();
        final List<Transaction> transactions = transactionRepository
                .findByMember_AddressAndDnaStartsWithAndIsSaleAndIsBurnOrderByRegDtAsc(userAddress, "1" + cropType.substring(3), false, false);
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

    public List<UserActivityItemResDto> findNftActivitesByUser(String userAddress, TrcListReqDto trcList, Pageable pageable) {
        final Profile user = userService.findProfile(userAddress);
        final List<NftHistory> nftHistoryList = nftHistoryRepository
                .findBySellerOrBuyerOrderByRegDtDesc(user, user, pageable);
        final List<UserActivityItemResDto> activities = new ArrayList<>();

        final List<String> allTRCs = codeService
                .getTransactionList()
                .orElseThrow()
                .stream()
                .map(c -> c.getCode())
                .collect(Collectors.toList());
        final List<String> trcs = trcList.getTrcList().isEmpty() ? allTRCs : trcList.getTrcList();

        for (NftHistory history : nftHistoryList) {
            final Transaction transaction = history.getTransaction();
            final String eventType = history.getEventType();

            if (trcs.contains(eventType)) {
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
                    .orElseGet(null);    //TODO 조합 후 nft가 삭제되는 경우 등에 대처하는 로직 필요 DB엔 on delete cascade 설정해두긴함

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

    public List<TopItemResDto> findTopItems() {
        final PriorityQueue<TopItem> pq = new PriorityQueue<>();
        final Iterable<Transaction> transactions = transactionRepository.findAll();

        for (Transaction transaction : transactions) {
            final Long transactionId = transaction.getId();
            final Double totalVolume = findTotalVolumeByTransactionId(transactionId);
            pq.offer(new TopItem(transaction, totalVolume));
        }

        final List<TopItemResDto> dtos = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            final Transaction transaction = pq.poll().getTransaction();
            final TopItemResDto dto = TopItemResDto.of(transaction, i);
            dtos.add(dto);
        }
        return dtos;
    }

    public List<TrendingItemResDto> findTrendingItems() {
        final CodeListResDto codeListResDto = codeService.findCodeList();
        final List<CodeListItem> cropCodes = codeListResDto.getCodeList().get(5).get("CROP");

        final PriorityQueue<TrendingItem> pq = new PriorityQueue<>();
        for (CodeListItem cropCode : cropCodes) {
            final String cropType = cropCode.getCode();
            final String codeNum = cropType.substring(3);
            final Integer totalNumOfActivities = nftHistoryRepository
                    .findByTransaction_DnaStartsWithOrTransaction_DnaStartsWith("1" + codeNum, "2" + codeNum)
                    .size();    //TODO 원래 전날 하루 기준인데 더미 데이터가 적어서 일단 전체 조회
            pq.offer(new TrendingItem(cropType, totalNumOfActivities));
        }

        final List<TrendingItemResDto> dtos = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            final TrendingItem trendingItem = pq.poll();

            final String cropType = trendingItem.getCropType();
            final MyCode code = codeService.getCode(MyCode.class, cropType);
            final MyCropCode myCropCode = codeService.getCode(MyCropCode.class, cropType);

            final String imgUrl = code.getPath();
            final String cropParent = capitalizeFirst(myCropCode.getPlural());
            final Double floorPrice = findFloorPrice(cropType);
            final Integer volume = findTotalVolumeByCropType(cropType);

            final TrendingItemResDto dto = TrendingItemResDto.of(
                    i, cropType, imgUrl, cropParent, floorPrice, volume
            );
            dtos.add(dto);
        }
        return dtos;
    }

    public void registerListing(ListingReqDto listingReqDto) {
        final String userAddress = authService.findCurrentOrAnonymousUser();
        final Transaction transaction = transactionRepository
                .findById(listingReqDto.getTransactionId())
                .orElseThrow();    //TODO 예외 처리
        final String ownerAddress = transaction.getMember().getAddress();

        if (!userAddress.equals(ownerAddress)) {
            throw new IllegalSignatureException();
        }

        if (transaction.getIsSale()) {
            throw new RuntimeException();    //TODO 예외 처리
        }

        final Profile seller = profileRepository.findById(userAddress)
                .orElseThrow(UserNotFoundException::new);
        final Double price = listingReqDto.getPrice();
        // list history 저장
        nftHistoryRepository
                .save(new NftHistory(transaction, seller, null, price, "TRC02"));
        // transaction 변경
        transaction.updateTransaction(transaction.getMember(), price, true);

        sendMessageToFavoritedMember(transaction, "즐겨찾기한 NFT가 거래를 시작헀어요~!");
    }

    public void registerCancel(Long transactionId) {
        final String userAddress = authService.findCurrentOrAnonymousUser();
        final Transaction transaction = transactionRepository
                .findById(transactionId)
                .orElseThrow(); //TODO 예외
        final Profile owner = transaction.getMember();

        if (!userAddress.equals(owner.getAddress())) {
            throw new IllegalSignatureException();
        }

        if (!transaction.getIsSale()) {
            throw new RuntimeException();    //TODO 예외 처리
        }

        // cancel history 저장
        nftHistoryRepository
                .save(new NftHistory(transaction, owner, null, -1.0, "TRC05"));
        // transaction 변경
        transaction.updateTransaction(owner, -1.0, false);
    }

    public void registerSaleNTransfer(Long transactionId) {
        final String userAddress = authService.findCurrentOrAnonymousUser();
        final Transaction transaction = transactionRepository
                .findById(transactionId)
                .orElseThrow(); //TODO 예외

        if (!transaction.getIsSale()) {
            throw new RuntimeException();    //TODO 예외 처리
        }

        final Profile seller = transaction.getMember();
        final Profile buyer = profileRepository.findById(userAddress)
                .orElseThrow(UserNotFoundException::new);

        // sale & transfer history 저장
        nftHistoryRepository
                .save(new NftHistory(transaction, seller, buyer, transaction.getPrice(), "TRC03"));
        nftHistoryRepository
                .save(new NftHistory(transaction, seller, buyer, transaction.getPrice(), "TRC04"));

        // transaction 변경
        transaction.updateTransaction(buyer, -1.0, false);
        sendMessageToFavoritedMember(transaction, "즐겨찾기한 NFT의 판매가 종료되었습니다!");
    }

    public FusionResDto checkIsDuplicated(FusionReqDto fusionReqDto) {
        // NFT 2개의 사용자가 로그인된 사용자가 맞는지 확인
        final String userAddress = authService.findCurrentOrAnonymousUser();
        final Profile user = userService.findProfile(userAddress);

        final Transaction transaction1 = transactionRepository
                .findById(fusionReqDto.getTransactionId1())
                .orElseThrow(); //TODO 예외
        final Transaction transaction2 = transactionRepository
                .findById(fusionReqDto.getTransactionId2())
                .orElseThrow(); //TODO 예외
        final String trc1user = transaction1.getMember().getAddress();
        final String trc2user = transaction2.getMember().getAddress();

        if (!userAddress.equals(trc1user) || !userAddress.equals(trc2user)) {
            throw new IllegalSignatureException();
        }

        // 합성에 사용된 NFT 2개 isBurn = false로 update
        transaction1.burnTransaction();
        transaction2.burnTransaction();

        // dna decode 해서 2 티어 NFT에서 중복 여부 판단
        final String decimalizedDna = decimalizeDna(fusionReqDto.getNewNft().getDna());
        final Boolean isDuplicated = transactionRepository.findByDna(decimalizedDna).isPresent();

        // 중복이면 isDupliacted = true, 다른 거 다 빈 스트링("")으로 dto 반환
        // 중복이 아니면 isDuplicated = false, 다른 거 imgUrl 반환
        final FusionResDto fusionResDto;
        if (isDuplicated) {
            fusionResDto = FusionResDto.duplicated();
        } else {
            final List<String> attributes = decodeDna(decimalizedDna);
            final FusionReqDto.NewNft newNft = fusionReqDto.getNewNft();

            fusionResDto = FusionResDto.builder()
                    .isDuplicated(false)
                    .cropTypeUrl(codeService.getCode(MyCode.class, attributes.get(0)).getPath())
                    .bgUrl(codeService.getCode(MyCode.class, attributes.get(1)).getPath())
                    .eyesUrl(codeService.getCode(MyCode.class, attributes.get(2)).getPath())
                    .mouthUrl(codeService.getCode(MyCode.class, attributes.get(3)).getPath())
                    .headGearUrl(codeService.getCode(MyCode.class, attributes.get(4)).getPath())
                    .build();
            // transaction에 등록
            transactionRepository.save(new Transaction(
                    user,
                    CONTRACT_ADDRESS,
                    newNft.getTokenId(),
                    decimalizedDna)
            );
        }
        return fusionResDto;
    }

    public String uploadNftImg(Long tokenId, MultipartFile nftImgFile) {
        return awsS3Utils.upload(nftImgFile, tokenId.toString(), S3Dir.NFT)
                .orElseThrow();    //TODO 예외
    }

    public Transaction registerFusion(Long tokenId, String imgUrl) {
        final String userAddress = authService.findCurrentOrAnonymousUser();
        final Profile buyer = userService.findProfile(userAddress);
        final Transaction transaction = transactionRepository
                .findByTokenId(tokenId)
                .orElseThrow();    //TODO 예외

        // transaction imgUrl 수정
        transaction.updateImgUrl(imgUrl);

        // nft_history에 등록
        nftHistoryRepository
                .save(new NftHistory(
                        transaction,
                        null,
                        buyer,
                        transaction.getPrice(),
                        "TRC01"));

        return transaction;
    }

    public List<AlbumItemResDto> searchNfts(
            SearchReqDto searchReqDto
    ) {
        final String keyword = searchReqDto.getKeyword();
        final String sort = searchReqDto.getSort();
        final List<String> tier = searchReqDto.getTier();
        final List<String> cropType = searchReqDto.getCropType();
        final List<String> bg = searchReqDto.getBg();
        final List<String> eyes = searchReqDto.getEyes();
        final List<String> mouth = searchReqDto.getMouth();
        final List<String> headGear = searchReqDto.getHeadGear();

        final String lowerCaseKeyword = keyword; // null 일 수 있으니까 처리해줘야함
        final String code = codeService.AttrToId(lowerCaseKeyword).orElseGet(null);

        //TODO sort

        // name에 일치하는게 있으면 그거의 코드랑 타입을 받아서 타입에 맞게 다른 파라미터 조회 위치에 넣어주기
        // 없으면 뭐 내려주지
        // 키워드가 Null인 경우 모두 조회
        // 나머지 파라미터는 순서에 맞게만 넣어주기
        final List<AlbumItemResDto> dtos = new ArrayList<>();
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
                .findByDnaStartsWithAndDnaStartsWithAndIsSaleAndIsBurn(
                        1 + codeNum,
                        2 + codeNum,
                        true,
                        false
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

    private String decimalizeDna(String dna) {
        String decimalizedDna = "2";
        for (int i = 0; i < 5; i++) {
            final String mod = String.valueOf(
                    Integer.parseInt(dna.substring(2 * i + 1, 2 * i + 3)) % 10);
            final String codeNum = mod.equals("0") ? "1" + mod : "0" + mod;
            decimalizedDna += codeNum;
        }
        return decimalizedDna;
    }

    private Double findTotalVolumeByTransactionId(Long transactionId) {
        final List<NftHistory> saleHistories = nftHistoryRepository
                .findByTransaction_IdAndEventType(transactionId, "TRC03");
        Double totalVolume = 0.0;

        for (NftHistory history : saleHistories) {
            totalVolume += history.getPrice();
        }

        return totalVolume;
    }

    @Getter
    private static class TopItem implements Comparable<TopItem> {
        Transaction transaction;
        Double totalVolume;

        private TopItem(Transaction transaction, Double totalVolume) {
            this.transaction = transaction;
            this.totalVolume = totalVolume;
        }

        @Override
        public int compareTo(@NotNull TopItem topItem) {
            return this.totalVolume <= topItem.totalVolume ? 1 : -1;
        }
    }

    @Getter
    private static class TrendingItem implements Comparable<TrendingItem> {
        String cropType;
        Integer totalNumOfActivities;

        private TrendingItem(String cropType, Integer totalNumOfActivities) {
            this.cropType = cropType;
            this.totalNumOfActivities = totalNumOfActivities;
        }

        @Override
        public int compareTo(@NotNull TrendingItem trendingItem) {
            return this.totalNumOfActivities <= trendingItem.totalNumOfActivities ? 1 : -1;
        }
    }

    private void sendMessageToFavoritedMember(Transaction transaction, String message){
        favoriteRepository.findByTransaction(transaction).forEach(favorite -> {
            Member member = favorite.getMember();
            notiService.sendMessage(member.getAddress(), NotiTitle.NFT, message);
        });
    }
}
