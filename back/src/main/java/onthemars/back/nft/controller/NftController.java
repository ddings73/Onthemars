package onthemars.back.nft.controller;

import static java.time.LocalDateTime.now;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import onthemars.back.nft.dto.response.NftActivityListResDto;
import onthemars.back.nft.dto.response.NftCropTypeDetailResDto;
import onthemars.back.nft.dto.response.NftDetailResDto;
import onthemars.back.nft.dto.response.NftAlbumListResDto;
import onthemars.back.nft.dto.response.NftTrendingListResDto;
import onthemars.back.nft.entity.Favorite;
import onthemars.back.nft.entity.Nft;
import onthemars.back.nft.entity.NftHistory;
import onthemars.back.nft.entity.Transaction;
import onthemars.back.nft.service.NftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * @return
     */
    @GetMapping("/detail/{nftId}")
    public ResponseEntity<NftDetailResDto> findNft(
        @PathVariable("nftId") String nftId
    ) {
        final Nft nft = nftService.findNftById(nftId);
        final String ownerNickname = nftService.findOwnerNickname();
        final Double price = nftService.findCurrentPrice();
        final LocalDateTime lastUpdate = nftService.findLastUpdate();
        final Transaction transaction = nftService.findTransactionByNftId(nftId);
        //TODO: MyCropCode랑 Favorite 추가

        final NftDetailResDto nftDto = NftDetailResDto.from(
            nft, ownerNickname, price, lastUpdate, transaction
        );
        return ResponseEntity.ok().build();
    }

    /**
     * NFT 작물 종류 상세 조회
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
     * @return
     */
    @PutMapping("/favorite/{nftId}")
    public ResponseEntity<Void> updateFavoriteNft() {
        return ResponseEntity.ok().build();
    }

    /**
     * NFT 판매 등록
     * @return
     */
    @PutMapping("/list")
    public ResponseEntity<Void> registerNftListing() {
        return ResponseEntity.ok().build();
    }

    /**
     * NFT 구매
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
