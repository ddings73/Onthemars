package onthemars.back.nft.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import onthemars.back.nft.dto.response.NftAttributesDto.Attribute;
import onthemars.back.nft.entity.Nft;
import onthemars.back.nft.entity.Transaction;

@Data
public class NftDetailResDto implements Serializable {

    public static NftDetailResDto of(
        Transaction transaction,
        List<Attribute> attributes,
        LocalDateTime lastUpdate,
        String cropParent
//        Favorite favorite
    ) {
        final Nft nft = transaction.getNft();
        final String cropParentCap =
            cropParent.charAt(0) + cropParent.substring(1).toLowerCase();
        final Info info = Info.of(nft, attributes, lastUpdate);

        return NftDetailResDto.builder()
            .ownerNickname(transaction.getNft().getMember().getNickname())
            .cropParent(cropParentCap)
            .nftName(cropParentCap + " #" + nft.getTokenId())
            .viewCnt(transaction.getViewCnt())
            .price(transaction.getPrice())
            .tier(transaction.getNft().getTier())
            .activated(transaction.getActivated())
//TODO 회원 생성 후 .isFavorite(favorite.getActivated())
            .isFavorite(false)
            .imgUrl(
                "https://onthemars-dev.s3.ap-northeast-2.amazonaws.com/images/background-color/05.png")
            .info(info)
            .build();
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Info {

        //TODO 공통코드 작성 후 type, bg, eyes, mouth, headGear 추가
        private List<Attribute> attributes;
        private String address;
        private String tokenId;
        private String tokenStandard;
        private String chain;
        private LocalDateTime lastUpdated;
        private String dna;

        public static Info of(
            Nft nft,
            List<Attribute> attributes,
            LocalDateTime lastUpdate
        ) {
            return Info.builder()
                .attributes(attributes)
                .address(nft.getAddress())
                .tokenId(nft.getTokenId())
                .tokenStandard("ERC-721")    //TODO nft 끝내고 바뀌면 변경하기
                .chain("Ethereum")
                .lastUpdated(lastUpdate)
                .dna(nft.getDna())
                .build();
        }

    }

    private final String ownerNickname;
    private final String cropParent;
    private final String nftName;
    private final Integer viewCnt;
    private final Double price;
    private final Integer tier;
    private final Boolean activated;
    private final Boolean isFavorite;
    private final String imgUrl;
    private final Info info;

    @Builder
    public NftDetailResDto(
        String ownerNickname,
        String cropParent,
        String nftName,
        Integer viewCnt,
        Double price,
        Integer tier,
        Boolean activated,
        Boolean isFavorite,
        String imgUrl,
        Info info
    ) {
        this.ownerNickname = ownerNickname;
        this.cropParent = cropParent;
        this.nftName = nftName;
        this.viewCnt = viewCnt;
        this.price = price;
        this.tier = tier;
        this.activated = activated;
        this.isFavorite = isFavorite;
        this.imgUrl = imgUrl;
        this.info = info;
    }
}
