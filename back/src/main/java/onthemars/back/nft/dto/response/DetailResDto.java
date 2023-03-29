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
        String nftName,
        Boolean isOwner,
        Boolean isFavorite
    ) {
        final Info info = Info.of(transaction, attributes, lastUpdate);

        return DetailResDto.builder()
            .ownerNickname(transaction.getMember().getNickname())
            .cropParent(cropParent)
            .nftName(nftName)
            .viewCnt(transaction.getViewCnt())
            .price(transaction.getPrice())
            .tier(Integer.valueOf(transaction.getDna().charAt(0)))
            .activated(transaction.getIsSale())
            .isOwner(isOwner)
            .isFavorite(isFavorite)
            .imgUrl(transaction.getImgUrl())
            .info(info)
            .build();
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Info {

        private List<Attribute> attributes;
        private Long transactionId;
        private String tokenId;
        private String tokenStandard;
        private String chain;
        private LocalDateTime lastUpdated;
        private String dna;

        public static Info of(
            Transaction transaction,
            List<Attribute> attributes,
            LocalDateTime lastUpdate
        ) {
            return Info.builder()
                .attributes(attributes)
                .transactionId(transaction.getId())
                .tokenId(transaction.getTokenId())
                .tokenStandard("ERC-721")
                .chain("Ethereum")
                .lastUpdated(lastUpdate)
                .dna(transaction.getDna())
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
    private final Boolean isOwner;
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
        Boolean isOwner, Boolean isFavorite,
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
        this.isOwner = isOwner;
        this.isFavorite = isFavorite;
        this.imgUrl = imgUrl;
        this.info = info;
    }
}
