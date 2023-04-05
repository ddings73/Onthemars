package onthemars.back.farm.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import onthemars.back.farm.app.CropDto;
import onthemars.back.farm.app.Player;
import onthemars.back.user.domain.Profile;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@ToString
public class LoadResDto {

    private Player player;

    private List<CropDto> cropList;


    public static LoadResDto of(Profile profile, List<CropDto> cropList) {
        return LoadResDto.builder()
            .player(Player.of(profile))
            .cropList(cropList)
            .build();
    }

}
