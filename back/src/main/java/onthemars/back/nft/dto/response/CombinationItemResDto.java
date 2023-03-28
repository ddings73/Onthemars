package onthemars.back.nft.dto.response;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;
import onthemars.back.nft.entity.Nft;

@Data
public class CombinationItemResDto implements Serializable {

    public static CombinationItemResDto of(
        Nft nft
    ) {
        return CombinationItemResDto.builder()
            .address(nft.getAddress())
            .imgUrl(nft.getImgUrl())
            .build();
    }

    @Builder
    public CombinationItemResDto(
        String address,
        String imgUrl
    ) {
        this.address = address;
        this.imgUrl = imgUrl;
    }

    private final String address;
    private final String imgUrl;

}
