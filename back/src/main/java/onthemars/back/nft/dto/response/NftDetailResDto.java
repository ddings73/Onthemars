package onthemars.back.nft.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class NftDetailResDto implements Serializable {

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

}
