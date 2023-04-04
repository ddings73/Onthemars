package onthemars.back.nft.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
public class FusionResDto {

    public static FusionResDto duplicated() {
        return FusionResDto.builder()
                .isDuplicated(true)
                .cropTypeUrl("")
                .bgUrl("")
                .eyesUrl("")
                .mouthUrl("")
                .headGearUrl("")
                .build();
    }
    @Builder
    public FusionResDto(
        Boolean isDuplicated,
        String cropTypeUrl,
        String bgUrl,
        String eyesUrl,
        String mouthUrl,
        String headGearUrl
    ) {
        this.isDuplicated = isDuplicated;
        this.cropTypeUrl = cropTypeUrl;
        this.bgUrl = bgUrl;
        this.eyesUrl = eyesUrl;
        this.mouthUrl = mouthUrl;
        this.headGearUrl = headGearUrl;
    }

    private final Boolean isDuplicated;
    private final String cropTypeUrl;
    private final String bgUrl;
    private final String eyesUrl;
    private final String mouthUrl;
    private final String headGearUrl;
}
