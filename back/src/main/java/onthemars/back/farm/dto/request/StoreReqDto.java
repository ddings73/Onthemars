package onthemars.back.farm.dto.request;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import onthemars.back.farm.app.CropList;
import onthemars.back.farm.app.Player;
import onthemars.back.farm.domain.Crop;
import onthemars.back.user.domain.Profile;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@ToString
public class StoreReqDto {

    private Player player;

    private CropList cropList;

    private List<MultipartFile> nftImgFile;


    public static StoreReqDto of(Profile profile, List<Crop> cropList,
        List<MultipartFile> nftImgFile) {
        return StoreReqDto.builder()
            .player(Player.of(profile))
            .cropList(CropList.of(cropList))
            .nftImgFile(nftImgFile)
            .build();
    }

}
