package onthemars.back.nft.dto.response;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;
import onthemars.back.nft.entity.Transaction;

@Data
public class TopItemResDto implements Serializable {

    public static TopItemResDto of(
        Transaction transaction,
        Integer rank
    ) {
        return TopItemResDto.builder()
            .rank(rank)
            .transactionId(transaction.getId())
            .imgUrl(transaction.getImgUrl())
            .build();
    }

    @Builder
    public TopItemResDto(Integer rank, Long transactionId, String imgUrl) {
        this.rank = rank;
        this.transactionId = transactionId;
        this.imgUrl = imgUrl;
    }

    private final Integer rank;
    private final Long transactionId;
    private final String imgUrl;

}
