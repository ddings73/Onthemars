package onthemars.back.nft.dto.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
public class TrendingItemResDto implements Serializable {

    public static TrendingItemResDto of(
            Integer rank,
            String cropType,
            String imgUrl,
            String cropParent,
            Double floorPrice,
            Integer volume
    ) {
        return TrendingItemResDto.builder()
                .rank(rank)
                .cropType(cropType)
                .imgUrl(imgUrl)
                .cropParent(cropParent)
                .floorPrice(floorPrice)
                .volume(volume)
                .build();
    }

    @Builder
    public TrendingItemResDto(
            Integer rank,
            String cropType,
            String imgUrl,
            String cropParent,
            Double floorPrice,
            Integer volume
    ) {
        this.rank = rank;
        this.cropType = cropType;
        this.imgUrl = imgUrl;
        this.cropParent = cropParent;
        this.floorPrice = floorPrice;
        this.volume = volume;
    }

    private final Integer rank;
    private final String cropType;
    private final String imgUrl;
    private final String cropParent;
    private final Double floorPrice;
    private final Integer volume;
}
