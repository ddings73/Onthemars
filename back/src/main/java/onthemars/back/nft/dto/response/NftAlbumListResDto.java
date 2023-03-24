package onthemars.back.nft.dto.response;

import java.io.Serializable;
import lombok.Data;

@Data
public class NftAlbumListResDto implements Serializable {
    private final String address;
    private final String tokenId;
    //TODO type, bg, eyes, mouth, headGear

}
