package onthemars.back.nft.controller;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onthemars.back.code.service.CodeService;
import onthemars.back.nft.dto.response.ActivityItemResDto;
import onthemars.back.nft.dto.response.AlbumItemResDto;
import onthemars.back.nft.dto.response.CropTypeDetailResDto;
import onthemars.back.nft.dto.response.DetailResDto;
import onthemars.back.nft.dto.response.TopItemResDto;
import onthemars.back.nft.dto.response.TrendingItemResDto;
import onthemars.back.nft.dto.response.UserActivityItemResDto;
import onthemars.back.nft.repository.NftHistoryRepository;
import onthemars.back.nft.repository.TransactionRepository;
import onthemars.back.nft.service.NftService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/nft")
public class NftController {

    private final TransactionRepository transactionRepository;
    private final NftHistoryRepository nftHistoryRepository;
    private final NftService nftService;
    private final CodeService codeService;

    //TODO 목록 조회 서비스단에서 최대한 처리하도록 리팩터링

    /**
     * NFT 상세 조회
     */
    @GetMapping("/detail/{nftAddress}")
    public ResponseEntity<DetailResDto> findNft(
        @PathVariable("nftAddress") String nftAddress
    ) {
        final DetailResDto detailResDto = nftService.findNftDetail(nftAddress);
        return ResponseEntity.ok(detailResDto);
    }

    /**
     * NFT Activity 목록 조회
     */
    @GetMapping("/activity/{nftAddress}")
    public ResponseEntity<List<ActivityItemResDto>> findNftActivities(
        @PathVariable("nftAddress") String nftAddress,
        @PageableDefault Pageable pageable
    ) {
        final List<ActivityItemResDto> activities = nftService
            .findNftActivitesDto(nftAddress, pageable);
        return ResponseEntity.ok(activities);
    }

    /**
     * NFT 목록 조회
     */
    @GetMapping("/list/{cropType}")
    public ResponseEntity<List<AlbumItemResDto>> findNftList(
        @PathVariable("cropType") String cropType
    ) {
        final List<AlbumItemResDto> nfts = nftService
            .findNfts(cropType);
        return ResponseEntity.ok(nfts);
    }

    /**
     * NFT 작물 종류 상세 조회
     */
    @GetMapping("/{cropType}")
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
    @GetMapping("/top")   //TODO 이거 REDIS 사용해서 어떻게 할 건지 생각해보고 작성하기
    public ResponseEntity<List<TopItemResDto>> findTopNfts() {
        return ResponseEntity.ok().build();
    }

    /**
     * NFT Trending 목록 조회
     */
    @GetMapping("/trending")    //TODO 이것도 REDIS
    public ResponseEntity<List<TrendingItemResDto>> findTrendingNfts() {
        final List<TrendingItemResDto> dummies = new ArrayList<>();
        final TrendingItemResDto dummy = new TrendingItemResDto(
            "carrots",
            0,
            0
        );
        dummies.add(dummy);
        return ResponseEntity.ok(dummies);
    }

    /**
     * NFT 찜
     */
    @PutMapping("/favorite/{nftAddress}")
    public ResponseEntity<Void> updateFavoriteNft() {
        return ResponseEntity.ok().build();
    }

    /**
     * NFT 판매 등록
     */
    @PutMapping("/list")
    public ResponseEntity<Void> registerNftListing() {
        return ResponseEntity.ok().build();
    }

    /**
     * NFT 구매
     */
    @PostMapping("/buy/{nftAddress}")
    public ResponseEntity<Void> registerNftSales() {
        return ResponseEntity.ok().build();
    }

    /**
     * NFT 합성
     */
    @PutMapping("/fusion")
    public ResponseEntity<Void> updateNftFusion() {
        return ResponseEntity.ok().build();
    }


    /**
     * 마이페이지 NFT Favorite
     */
    @GetMapping("/favorite")
    public ResponseEntity<List<AlbumItemResDto>> findFavoriteNfts() {
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
    @GetMapping("/{userAddress}/activity")
    public ResponseEntity<List<UserActivityItemResDto>> findNftActivitiesByUser(
        @PathVariable("userAddress") String userAddress,
        @PageableDefault Pageable pageable
    ) {
        final List<UserActivityItemResDto> activities = nftService
            .findNftActivitesByUser(userAddress, pageable);
        return ResponseEntity.ok(activities);
    }

    /**
     * NFT 검색
     */
    @PutMapping("/search")
    public ResponseEntity<Void> searchNft() {
        return ResponseEntity.ok().build();
    }

}
