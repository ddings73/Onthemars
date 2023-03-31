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

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@ToString
public class StoreReqDto {

    private Player player;

    private CropList cropList;


    public static StoreReqDto of(Profile profile, List<Crop> cropList) {
        return StoreReqDto.builder()
            .player(Player.of(profile))
            .cropList(CropList.of(cropList))
            .build();
    }

}
