package onthemars.back.nft.dto.response;

import lombok.Builder;
import lombok.Data;
import onthemars.back.nft.entity.Transaction;

import java.io.Serializable;

@Data
public class CombinationItemResDto implements Serializable {

    public static CombinationItemResDto of(
        Transaction transaction,
        String cropName
    ) {
        return CombinationItemResDto.builder()
            .transactionId(transaction.getId())
            .imgUrl(transaction.getImgUrl())
            .cropName(cropName)
            .tokenId(transaction.getTokenId())
            .contractAddress(transaction.getContractAddress())
            .build();
    }

    @Builder
    public CombinationItemResDto(
            Long transactionId,
            String imgUrl,
            String cropName, Long tokenId, String contractAddress) {
        this.transactionId = transactionId;
        this.imgUrl = imgUrl;
        this.cropName = cropName;
        this.tokenId = tokenId;
        this.contractAddress = contractAddress;
    }

    private final Long transactionId;
    private final String imgUrl;
    private final String cropName;
    private final Long tokenId;
    private final String contractAddress;

}
