package onthemars.back.nft.dto.response;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;
import onthemars.back.nft.entity.Nft;

@Data
public class TopItemResDto implements Serializable {

    public static TopItemResDto of(
        Nft nft,
        Integer rank
    ) {
        return TopItemResDto.builder()
            .rank(rank)
            .nftAddress(nft.getAddress())
            .imgUrl(nft.getImgUrl())
            .build();
    }

    @Builder
    public TopItemResDto(Integer rank, String nftAddress, String imgUrl) {
        this.rank = rank;
        this.nftAddress = nftAddress;
        this.imgUrl = imgUrl;
    }

    private final Integer rank;
    private final String nftAddress;
    private final String imgUrl;

}
