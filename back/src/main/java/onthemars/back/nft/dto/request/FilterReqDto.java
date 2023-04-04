package onthemars.back.nft.dto.request;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FilterReqDto {
    private Double minPrice;
    private Double maxPrice;
    private String sort;
    private List<String> tier;
    private List<String> bg;
    private List<String> eyes;
    private List<String> mouth;
    private List<String> headGear;
}
