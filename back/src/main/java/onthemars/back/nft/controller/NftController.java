package onthemars.back.nft.controller;

import lombok.extern.slf4j.Slf4j;
import onthemars.back.nft.dto.response.*;
import onthemars.back.nft.entity.Nft;
import onthemars.back.nft.entity.Transaction;
import onthemars.back.nft.service.NftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.now;

@Slf4j
@RestController
@RequestMapping("/nft")
public class NftController {

    private final NftService nftService;

    @Autowired
    public NftController(
            NftService nftService
    ) {
        this.nftService = nftService;
    }

    /**
     * NFT 상세 조회
     *
     * @return
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
     *
     * @return
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
     *
     * @return
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
     *
     * @return
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
     *
     * @return
     */
    @PutMapping("/favorite/{nftId}")
    public ResponseEntity<Void> updateFavoriteNft() {
        return ResponseEntity.ok().build();
    }

    /**
     * NFT 판매 등록
     *
     * @return
     */
    @PutMapping("/list")
    public ResponseEntity<Void> registerNftListing() {
        return ResponseEntity.ok().build();
    }

    /**
     * NFT 구매
     *
     * @return
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
    @GetMapping("activity")
    public ResponseEntity<List<NftActivityListResDto>> findNftActivities() {
        final LocalDateTime dummyDate = now();
        final List<NftActivityListResDto> dummies = new ArrayList<>();
        final NftActivityListResDto dummy = new NftActivityListResDto(
                "Sales",
                "Carrots",
                "Carrot",
                "token-id",
                10.04,
                "seller",
                "buyer",
                dummyDate
        );
        dummies.add(dummy);
        return ResponseEntity.ok(dummies);
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
