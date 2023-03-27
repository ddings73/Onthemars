package onthemars.back.nft.dto.response;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;
import onthemars.back.code.app.MyCropCode;

@Data
public class CropTypeDetailResDto implements Serializable {

    public static CropTypeDetailResDto of(
        MyCropCode myCropCode,
        String cropParent,
        Integer totalVolume,
        Double floorPrice,
        Integer listed,
        Integer mintedCnt
    ) {
        return CropTypeDetailResDto.builder()
            .cropParent(cropParent)
            .cropBio(myCropCode.getBio())
            .totalVolume(totalVolume)
            .floorPrice(floorPrice)
            .listed(listed)
            .mintedCnt(mintedCnt)
            .build();
    }

    @Builder
    public CropTypeDetailResDto(
        String cropParent,
        String cropBio,
        Integer totalVolume,
        Double floorPrice,
        Integer listed,
        Integer mintedCnt
    ) {
        this.cropParent = cropParent;
        this.cropBio = cropBio;
        this.totalVolume = totalVolume;
        this.floorPrice = floorPrice;
        this.listed = listed;
        this.mintedCnt = mintedCnt;
    }

    private final String cropParent;
    private final String cropBio;
    private final Integer totalVolume;
    private final Double floorPrice;
    private final Integer listed;
    private final Integer mintedCnt;
}
