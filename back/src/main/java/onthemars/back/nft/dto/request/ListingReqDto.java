package onthemars.back.nft.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ListingReqDto {

    private Long transactionId;
    private Double price;

}
