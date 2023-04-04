package onthemars.back.farm.dto.request;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import onthemars.back.farm.app.CropDto;
import onthemars.back.farm.app.Player;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@ToString
public class StoreReqDto {

    private Player player;

    private List<CropDto> cropList;


}
