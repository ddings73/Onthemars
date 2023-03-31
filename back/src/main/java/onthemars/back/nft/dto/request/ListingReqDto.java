package onthemars.back.nft.dto.request;

import java.time.Instant;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import onthemars.back.nft.entity.NftHistory;
import onthemars.back.nft.entity.Transaction;
import onthemars.back.user.domain.Profile;

@Data
@NoArgsConstructor
public class ListingReqDto {

    private Long transactionId;
    private Double price;

}
