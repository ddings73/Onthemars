package onthemars.back.farm.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class SeedHistoryReqDto {

    private Integer cnt;
    private Double price;
}
