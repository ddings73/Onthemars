package onthemars.back.nft.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import onthemars.back.nft.entity.NftHistory;

@Data
public class GraphItemResDto {

    public static GraphItemResDto of(NftHistory nftHistory) {
        return GraphItemResDto.builder()
            .date(nftHistory.getRegDt())
            .price(nftHistory.getPrice())
            .build();
    }

    @Builder
    public GraphItemResDto(
        LocalDateTime date,
        Double price
    ) {
        this.date = date;
        this.price = price;
    }

    private LocalDateTime date;
    private Double price;
}
