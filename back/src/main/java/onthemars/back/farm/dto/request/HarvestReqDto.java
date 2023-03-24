package onthemars.back.farm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class HarvestReqDto {

    private Long cropId;
    private String address;
    private String tokenId;
    private String type;
    private String bg;
    private String tier;
    private String dna;
}
