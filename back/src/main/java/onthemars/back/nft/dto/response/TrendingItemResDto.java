package onthemars.back.nft.dto.response;

import java.io.Serializable;
import lombok.Data;

@Data
public class TrendingItemResDto implements Serializable {
    private final String cropParent;
    private final Integer floorPrice;
    private final Integer Volume;
}
