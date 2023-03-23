package onthemars.back.farm.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class PlantReqDto {

    private Integer rowNum;

    private Integer colNum;
}
