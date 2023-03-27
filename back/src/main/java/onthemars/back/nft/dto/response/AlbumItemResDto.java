package onthemars.back.nft.dto.response;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;
import onthemars.back.nft.entity.Transaction;

@Data
public class AlbumItemResDto implements Serializable {

    public static AlbumItemResDto of(
        Transaction transaction,
        String nftName,
        Double price,
        Double lastSalePrice
    ) {
        return AlbumItemResDto.builder()
            .imgUrl(transaction.getNft().getImgUrl())
            .nftName(nftName)
            .price(price)
            .lastSalePrice(lastSalePrice)
            .address(transaction.getNft().getAddress())
            .build();
    }

    @Builder
    public AlbumItemResDto(
        String imgUrl,
        String nftName,
        Double price,
        Double lastSalePrice,
        String address
    ) {
        this.imgUrl = imgUrl;
        this.nftName = nftName;
        this.price = price;
        this.lastSalePrice = lastSalePrice;
        this.address = address;
    }

    private final String imgUrl;
    private final String nftName;
    private final Double price;
    private final Double lastSalePrice;
    private final String address;

}
