package onthemars.back.nft.dto.response;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;
import onthemars.back.nft.entity.Transaction;

@Data
public class AlbumItemResDto implements Serializable {

    public static AlbumItemResDto of(
        Transaction transaction
    ) {
        return AlbumItemResDto.builder()
            .transactionId(transaction.getId())
            .imgUrl(transaction.getImgUrl())
            .build();
    }

    @Builder
    public AlbumItemResDto(
        Long transactionId,
        String imgUrl
    ) {
        this.transactionId = transactionId;
        this.imgUrl = imgUrl;
    }

    private final Long transactionId;
    private final String imgUrl;

}
