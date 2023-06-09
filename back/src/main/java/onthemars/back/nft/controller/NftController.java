package onthemars.back.nft.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onthemars.back.nft.dto.request.FilterReqDto;
import onthemars.back.nft.dto.request.FusionReqDto;
import onthemars.back.nft.dto.request.ListingReqDto;
import onthemars.back.nft.dto.request.SearchReqDto;
import onthemars.back.nft.dto.request.TrcListReqDto;
import onthemars.back.nft.dto.response.*;
import onthemars.back.nft.service.NftService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/nft")
public class NftController {

    private final NftService nftService;

    /**
     * NFT 상세 조회
     */
    @GetMapping("/detail/{transactionId}")
    public ResponseEntity<DetailResDto> findNft(
            @PathVariable("transactionId") Long transactionId
    ) {
        final DetailResDto detailResDto = nftService.findNftDetail(transactionId);
        return ResponseEntity.ok(detailResDto);
    }

    /**
     * NFT Activity 목록 조회
     */
    @GetMapping("/activity/{transactionId}")
    public ResponseEntity<List<ActivityItemResDto>> findNftActivities(
            @PathVariable("transactionId") Long transactionId,
            @PageableDefault Pageable pageable
    ) {
        final List<ActivityItemResDto> activities = nftService
                .findNftActivitesDto(transactionId, pageable);
        return ResponseEntity.ok(activities);
    }

    /**
     * NFT 상세 Activity 그래프
     */
    @GetMapping("/graph/{transactionId}")
    public ResponseEntity<List<GraphItemResDto>> findNftActivitiesForGraph(
            @PathVariable("transactionId") Long transactionId
    ) {
        final List<GraphItemResDto> graphItems = nftService.findGraphItems(transactionId);
        return ResponseEntity.ok(graphItems);
    }

    /**
     * NFT 작물 종류별 목록 조회
     */
    @PostMapping("/list/{cropType}")
    public ResponseEntity<List<AlbumItemResDto>> findNftList(
            @PathVariable("cropType") String cropType,
            @RequestBody(required = false) FilterReqDto filterReqDto
    ) {
        final List<AlbumItemResDto> nfts = nftService
                .findNftsByCropType(cropType, filterReqDto);
        return ResponseEntity.ok(nfts);
    }

    /**
     * NFT 작물 종류 상세 조회
     */
    @GetMapping("/{cropType}/detail")
    public ResponseEntity<CropTypeDetailResDto> findCropTypeDetail(
            @PathVariable("cropType") String cropType
    ) {
        final CropTypeDetailResDto cropTypeDetail = nftService
                .findCropTypeDetail(cropType);
        return ResponseEntity.ok(cropTypeDetail);
    }

    /**
     * NFT Top 목록 조회
     */
    @GetMapping("/top")
    public ResponseEntity<List<TopItemResDto>> findTopNfts() {
        final List<TopItemResDto> topItems = nftService.findTopItems();
        return ResponseEntity.ok(topItems);
    }

    /**
     * NFT Trending 목록 조회
     */
    @GetMapping("/trending")
    public ResponseEntity<List<TrendingItemResDto>> findTrendingNfts() {
        final List<TrendingItemResDto> trendingItems = nftService.findTrendingItems();
        return ResponseEntity.ok(trendingItems);
    }

    /**
     * NFT 찜
     */
    @PutMapping("/favorite/{transactionId}")
    public ResponseEntity<Void> updateFavoriteNft(
            @PathVariable("transactionId") Long transactionId
    ) {
        nftService.updateFavoriteActivated(transactionId);
        return ResponseEntity.ok().build();
    }

    /**
     * NFT 판매 등록
     */
    @PostMapping("/history/listing")
    public ResponseEntity<Void> registerNftListing(
            @RequestBody ListingReqDto listingReqDto
            ) {
        nftService.registerListing(listingReqDto);
        return ResponseEntity.ok().build();
    }

    /**
     * NFT 판매 취소
     */
    @PostMapping("/history/cancel/{transactionId}")
    public ResponseEntity<Void> registerNftCancel(
        @PathVariable Long transactionId
    ) {
        nftService.registerCancel(transactionId);
        return ResponseEntity.ok().build();
    }

    /**
     * NFT 구매
     */
    @PostMapping("/history/sale/{transactionId}")
    public ResponseEntity<Void> registerNftSales(
            @PathVariable Long transactionId
    ) {
        nftService.registerSaleNTransfer(transactionId);
        return ResponseEntity.ok().build();
    }

    /**
     * NFT 조합 중복 체크
     */
    @PostMapping("/history/fusion")
    public ResponseEntity<FusionResDto> updateNftFusion(
            @RequestBody FusionReqDto fusionReqDto
            ) {
        final FusionResDto fusionResDto = nftService.checkIsDuplicated(fusionReqDto);
        return ResponseEntity.ok(fusionResDto);
    }

    /**
     * NFT 조합 성공 결과 저장
     */
    @PostMapping("/history/fusion/{transactionId}")
    public ResponseEntity<Void> registerNftFusion(
            @PathVariable Long transactionId,
            @RequestPart MultipartFile nftImgFile
    ) {
        final String imgUrl = nftService.uploadNftImg(transactionId, nftImgFile);
        nftService.registerFusion(transactionId, imgUrl);
        return ResponseEntity.ok().build();
    }

    /**
     * 마이페이지 NFT Collected
     */
    @GetMapping("/{userAddress}/collected")
    public ResponseEntity<List<AlbumItemResDto>> findCollectedNfts(
            @PathVariable("userAddress") String userAddress,
            @PageableDefault Pageable pageable
    ) {
        final List<AlbumItemResDto> collectedNfts = nftService
                .findCollectedNfts(userAddress, pageable);
        return ResponseEntity.ok(collectedNfts);
    }

    /**
     * 마이페이지 NFT Minted
     */
    @GetMapping("/{userAddress}/minted")
    public ResponseEntity<List<AlbumItemResDto>> findMintedNfts(
            @PathVariable("userAddress") String userAddress,
            @PageableDefault Pageable pageable
    ) {
        final List<AlbumItemResDto> mintedNfts = nftService
                .findMintedNfts(userAddress, pageable);
        return ResponseEntity.ok(mintedNfts);
    }

    /**
     * 마이페이지 NFT Activity
     */
    @PostMapping("/{userAddress}/activity")
    public ResponseEntity<List<UserActivityItemResDto>> findNftActivitiesByUser(
            @PathVariable("userAddress") String userAddress,
            @RequestBody(required = false) TrcListReqDto trcList,
            @PageableDefault Pageable pageable
    ) {
        log.info(String.valueOf(trcList));
        final List<UserActivityItemResDto> activities = nftService
                .findNftActivitesByUser(userAddress, trcList, pageable);
        return ResponseEntity.ok(activities);
    }

    /**
     * 마이페이지 NFT Favorited
     */
    @GetMapping("/{userAddress}/favorited")
    public ResponseEntity<List<AlbumItemResDto>> findFavoritedNfts(
            @PathVariable String userAddress,
            @PageableDefault Pageable pageable
    ) {
        final List<AlbumItemResDto> favoritedNftes = nftService
                .findFavoritedNfts(userAddress, pageable);
        return ResponseEntity.ok(favoritedNftes);
    }

    /**
     * 마이페이지 NFT Combination
     */
    @GetMapping("/combination")
    public ResponseEntity<List<CombinationItemResDto>> findNftsForCombination(
            @RequestParam String cropType
    ) {
        final List<CombinationItemResDto> combinationItems = nftService
            .findNftsForCombinationByCropType(cropType);
        return ResponseEntity.ok(combinationItems);
    }

    /**
     * NFT 검색
     */
    @PostMapping("/search")
    public ResponseEntity<List<AlbumItemResDto>> searchNft(
        @RequestBody SearchReqDto searchReqDto
    ) {
        final List<AlbumItemResDto> dtos = nftService.searchNfts(searchReqDto);
        return ResponseEntity.ok(dtos);
    }

}
