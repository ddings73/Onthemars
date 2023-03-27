package onthemars.back.nft.dto.response;

import java.io.Serializable;
import lombok.Data;

@Data
public class NftTrendingListResDto implements Serializable {
    private final String cropParent;
    //TODO 공통 코드 작성 후 type, bg, eyes, mouth, headGear 추가
    private final Integer floorPrice;
    private final Integer Volume;
}
