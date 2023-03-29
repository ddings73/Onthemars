package onthemars.back.farm.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class WaterReqDto {

    private Long cropId;

    private Boolean shorten;


}
