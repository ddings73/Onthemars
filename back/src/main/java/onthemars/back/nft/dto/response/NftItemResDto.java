package onthemars.back.nft.dto.response;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;
import onthemars.back.nft.entity.Transaction;

@Data
public class NftItemResDto implements Serializable {

    public static NftItemResDto of(
        Transaction transaction,
        String nftName,
        Double price,
        Double lastSalePrice
    ) {
        return NftItemResDto.builder()
            .imgUrl(transaction.getNft().getImgUrl())
            .nftName(nftName)
            .price(price)
            .lastSalePrice(lastSalePrice)
            .address(transaction.getNft().getAddress())
            .build();
    }

    @Builder
    public NftItemResDto(
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
