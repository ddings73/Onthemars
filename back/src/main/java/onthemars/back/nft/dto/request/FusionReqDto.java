package onthemars.back.nft.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
public class FusionReqDto {
    private Long transactionId1;
    private Long transactionId2;
    private NewNft newNft;

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NewNft {
        private Long tokenId;
        private String dna;
    }
}
