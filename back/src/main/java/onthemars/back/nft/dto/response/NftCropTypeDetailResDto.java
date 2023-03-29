package onthemars.back.nft.dto.response;

import java.io.Serializable;
import lombok.Data;

@Data
public class NftCropTypeDetailResDto implements Serializable {

    private final String backgroundImg;
    private final String cropProfileImg;
    private final Integer mintedCnt;
    private final String cropBio;
    private final String cropParent;
    private final Integer totalVolume;
    private final Integer floorPrice;
    private final Integer listed;
    private final Integer page;
    private final Integer size;

}
