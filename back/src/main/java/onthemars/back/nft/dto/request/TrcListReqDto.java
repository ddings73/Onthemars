package onthemars.back.nft.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TrcListReqDto {
    private List<String> trcList;

}
