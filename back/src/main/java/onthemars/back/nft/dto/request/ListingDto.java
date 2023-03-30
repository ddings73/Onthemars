package onthemars.back.nft.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ListingDto {

    private Long transactionId;
    private Double price;

}
