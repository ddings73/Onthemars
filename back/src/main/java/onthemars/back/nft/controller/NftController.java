package onthemars.back.nft.controller;

import static java.time.LocalDateTime.now;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onthemars.back.code.app.MyCode;
import onthemars.back.code.service.CodeService;
import onthemars.back.nft.dto.response.NftActivityItemResDto;
import onthemars.back.nft.dto.response.NftAlbumListResDto;
import onthemars.back.nft.dto.response.NftCropTypeDetailResDto;
import onthemars.back.nft.dto.response.NftDetailResDto;
import onthemars.back.nft.dto.response.NftTrendingListResDto;
import onthemars.back.nft.entity.NftHistory;
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

    private final NftService nftService;
    private final CodeService codeService;

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
     * NFT 작물 종류 상세 조회
     */
    @GetMapping("/{cropType}")
    public ResponseEntity<NftCropTypeDetailResDto> findNftsByCropType() {
        final NftCropTypeDetailResDto dummy = new NftCropTypeDetailResDto(
            "a.link.to.bg.img",
            "a.link.to.crop.profile.img",
            0,
            "description for crop",
            "Carrots",
            0,
            0,
            0,
            1,
            5
        );
        return ResponseEntity.ok(dummy);
    }

    /**
     * NFT Top 목록 조회
     */
    @GetMapping("/top")
    public ResponseEntity<List<NftAlbumListResDto>> findTopNfts() {
        final List<NftAlbumListResDto> dummies = new ArrayList<>();
        final NftAlbumListResDto dummy = new NftAlbumListResDto(
            "address",
            "token-id"
        );
        dummies.add(dummy);
        return ResponseEntity.ok(dummies);
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
     * NFT Favorite
     */
    @GetMapping("/favorite")
    public ResponseEntity<List<NftAlbumListResDto>> findFavoriteNfts() {
        final List<NftAlbumListResDto> dummies = new ArrayList<>();
        final NftAlbumListResDto dummy = new NftAlbumListResDto(
            "address",
            "token-id"
        );
        dummies.add(dummy);
        return ResponseEntity.ok(dummies);
    }

    /**
     * NFT Minted
     */
    @GetMapping("/minted")
    public ResponseEntity<List<NftAlbumListResDto>> findMintedNfts() {
        final List<NftAlbumListResDto> dummies = new ArrayList<>();
        final NftAlbumListResDto dummy = new NftAlbumListResDto(
            "address",
            "token-id"
        );
        dummies.add(dummy);
        return ResponseEntity.ok(dummies);
    }

    /**
     * NFT Minted
     */
    @GetMapping("/collected")
    public ResponseEntity<List<NftAlbumListResDto>> findCollectedNfts() {
        final List<NftAlbumListResDto> dummies = new ArrayList<>();
        final NftAlbumListResDto dummy = new NftAlbumListResDto(
            "address",
            "token-id"
        );
        dummies.add(dummy);
        return ResponseEntity.ok(dummies);
    }

    /**
     * NFT 검색
     */
    @PutMapping("/search")
    public ResponseEntity<Void> searchNft() {
        return ResponseEntity.ok().build();
    }

}
