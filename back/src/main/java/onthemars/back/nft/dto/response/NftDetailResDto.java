package onthemars.back.nft.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import onthemars.back.nft.entity.Nft;
import onthemars.back.nft.entity.Transaction;

@Data
public class NftDetailResDto implements Serializable {

    public static NftDetailResDto from(
        Nft nft,
        String ownerNickname,
        Double price,
        LocalDateTime lastUpdate,
//        MyCropCode myCropCode,
        Transaction transaction
//        Favorite favorite
    ) {
        return NftDetailResDto.builder()
            .ownerNickname(ownerNickname)
//            .cropParent(myCropCode.getPlural())
            .viewCnt(transaction.getViewCnt())
            .tokenId(nft.getTokenId())
            .price(price)
            .address(nft.getAddress())
            .tier(nft.getTier())
            .dna(nft.getDna())
            .activated(transaction.getActivated())
//            .isFavorite(favorite.getActivated())
            .lastUpdate(lastUpdate)
            .build();
    }

    private final String ownerNickname;
    private final String cropParent;
    private final String cropType;
    private final Integer viewCnt;
    private final String tokenId;
    private final Double price;
    private final String address;
    private final Integer tier;
    private final String dna;
    private final Boolean activated;
    private final Boolean isFavorite;
    //TODO 공통코드 작성 후 type, bg, eyes, mouth, headGear 추가
    private final LocalDateTime lastUpdate;

    @Builder
    public NftDetailResDto(
        String ownerNickname,
        String cropParent,
        String cropType,
        Integer viewCnt,
        String tokenId,
        Double price,
        String address,
        Integer tier,
        String dna,
        Boolean activated,
        Boolean isFavorite,
        LocalDateTime lastUpdate) {
        this.ownerNickname = ownerNickname;
        this.cropParent = cropParent;
        this.cropType = cropType;
        this.viewCnt = viewCnt;
        this.tokenId = tokenId;
        this.price = price;
        this.address = address;
        this.tier = tier;
        this.dna = dna;
        this.activated = activated;
        this.isFavorite = isFavorite;
        this.lastUpdate = lastUpdate;
    }

}
