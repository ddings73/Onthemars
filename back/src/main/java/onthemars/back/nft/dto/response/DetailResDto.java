package onthemars.back.nft.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import onthemars.back.nft.dto.response.AttributesDto.Attribute;
import onthemars.back.nft.entity.Transaction;

@Data
public class DetailResDto implements Serializable {

    public static DetailResDto of(
        Transaction transaction,
        List<Attribute> attributes,
        LocalDateTime lastUpdate,
        String cropParent,
        String nftName
//        Favorite favorite
    ) {
        final Nft nft = transaction.getNft();
        final String cropParentCap =
            cropParent.charAt(0) + cropParent.substring(1).toLowerCase();
        final Info info = Info.of(nft, attributes, lastUpdate);

        return DetailResDto.builder()
            .ownerNickname(transaction.getNft().getMember().getNickname())
            .cropParent(cropParentCap)
            .nftName(nftName)
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
                .tokenStandard("ERC-721")
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
    public DetailResDto(
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
