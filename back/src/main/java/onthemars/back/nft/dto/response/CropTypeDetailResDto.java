package onthemars.back.nft.dto.response;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;
import onthemars.back.code.app.MyCropCode;

@Data
public class CropTypeDetailResDto implements Serializable {

    public static CropTypeDetailResDto of(
        String backImg,
        String cardImg,
        MyCropCode myCropCode,
        String cropParent,
        Integer totalVolume,
        Double floorPrice,
        Integer listed,
        Integer mintedCnt
    ) {
        return CropTypeDetailResDto.builder()
            .backImg(backImg)
            .cardImg(cardImg)
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
            String backImg, String cardImg, String cropParent,
            String cropBio,
            Integer totalVolume,
            Double floorPrice,
            Integer listed,
            Integer mintedCnt
    ) {
        this.backImg = backImg;
        this.cardImg = cardImg;
        this.cropParent = cropParent;
        this.cropBio = cropBio;
        this.totalVolume = totalVolume;
        this.floorPrice = floorPrice;
        this.listed = listed;
        this.mintedCnt = mintedCnt;
    }

    private final String backImg;
    private final String cardImg;
    private final String cropParent;
    private final String cropBio;
    private final Integer totalVolume;
    private final Double floorPrice;
    private final Integer listed;
    private final Integer mintedCnt;
}
