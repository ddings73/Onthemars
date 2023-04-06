package onthemars.back.nft.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
public class FusionResDto {

    public static FusionResDto duplicated() {
        return FusionResDto.builder()
                .transactionId(-1L)
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
            Long transactionId,
            Boolean isDuplicated,
            String cropTypeUrl,
            String bgUrl,
            String eyesUrl,
            String mouthUrl,
            String headGearUrl
    ) {
        this.transactionId = transactionId;
        this.isDuplicated = isDuplicated;
        this.cropTypeUrl = cropTypeUrl;
        this.bgUrl = bgUrl;
        this.eyesUrl = eyesUrl;
        this.mouthUrl = mouthUrl;
        this.headGearUrl = headGearUrl;
    }

    private final Long transactionId;
    private final Boolean isDuplicated;
    private final String cropTypeUrl;
    private final String bgUrl;
    private final String eyesUrl;
    private final String mouthUrl;
    private final String headGearUrl;
}
