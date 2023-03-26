package onthemars.back.nft.controller;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onthemars.back.code.app.MyCode;
import onthemars.back.code.service.CodeService;
import onthemars.back.nft.dto.response.NftActivityItemResDto;
import onthemars.back.nft.dto.response.NftCropTypeDetailResDto;
import onthemars.back.nft.dto.response.NftDetailResDto;
import onthemars.back.nft.dto.response.NftItemResDto;
import onthemars.back.nft.dto.response.NftTrendingListResDto;
import onthemars.back.nft.entity.NftHistory;
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
import org.springframework.web.bind.annotation.RequestParam;
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
    @GetMapping("/detail/{nftId}")
    public ResponseEntity<NftDetailResDto> findNft(
        @PathVariable("nftId") String nftId
    ) {
        final NftDetailResDto nftDetailResDto = nftService.findNftDetail(nftId);
        return ResponseEntity.ok(nftDetailResDto);
    }

    /**
     * NFT Activity 목록 조회
     */
    @GetMapping("/activity/{nftId}")
    public ResponseEntity<List<NftActivityItemResDto>> findNftActivities(
        @PathVariable("nftId") String nftId,
        @PageableDefault Pageable pageable
    ) {
        final List<NftHistory> nftHistoryList = nftService.findNftActivites(nftId, pageable);
        final List<NftActivityItemResDto> activities = new ArrayList<>();

        for (NftHistory history : nftHistoryList) {
            final String event = codeService
                .getCode(MyCode.class, history.getEventType())
                .getName();
            final NftActivityItemResDto activity = NftActivityItemResDto.of(history, event);
            activities.add(activity);
        }
        return ResponseEntity.ok(activities);
    }

    /**
     * NFT 목록 조회
     */
    @GetMapping
    public ResponseEntity<List<NftItemResDto>> findNftList(
        @RequestParam(value = "cropType", required = false) String cropType
    ) {
        final List<NftItemResDto> nfts = nftService
            .findNfts(cropType);
        return ResponseEntity.ok(nfts);
    }

    /**
     * NFT 작물 종류 상세 조회
     */
    @GetMapping("/{cropType}")
    public ResponseEntity<NftCropTypeDetailResDto> findCropTypeDetail(
        @PathVariable("cropType") String cropType
    ) {
        final NftCropTypeDetailResDto cropTypeDetail = nftService
            .findCropTypeDetail(cropType);
        return ResponseEntity.ok(cropTypeDetail);
    }

    /**
     * NFT Top 목록 조회
     */
    @GetMapping("/top")
    public ResponseEntity<List<NftItemResDto>> findTopNfts() {

        return ResponseEntity.ok().build();
    }

    /**
     * NFT Trending 목록 조회
     */
    @GetMapping("/trending")
    public ResponseEntity<List<NftTrendingListResDto>> findTrendingNfts() {
        final List<NftTrendingListResDto> dummies = new ArrayList<>();
        final NftTrendingListResDto dummy = new NftTrendingListResDto(
            "carrots",
            0,
            0
        );
        dummies.add(dummy);
        return ResponseEntity.ok(dummies);
    }

    /**
     * NFT 좋아요
     */
    @PutMapping("/favorite/{nftId}")
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
    @PostMapping("/buy/{nftId}")
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
     * NFT Favorite
     */
    @GetMapping("/favorite")
    public ResponseEntity<List<NftItemResDto>> findFavoriteNfts() {
        return ResponseEntity.ok().build();
    }

    /**
     * NFT Minted
     */
    @GetMapping("/minted")
    public ResponseEntity<List<NftItemResDto>> findMintedNfts() {
        return ResponseEntity.ok().build();
    }

    /**
     * NFT Minted
     */
    @GetMapping("/collected")
    public ResponseEntity<List<NftItemResDto>> findCollectedNfts() {
        return ResponseEntity.ok().build();
    }

    /**
     * NFT 검색
     */
    @PutMapping("/search")
    public ResponseEntity<Void> searchNft() {
        return ResponseEntity.ok().build();
    }

}
