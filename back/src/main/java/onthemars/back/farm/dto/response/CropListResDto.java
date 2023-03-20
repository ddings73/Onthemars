package onthemars.back.farm.dto.response;

import java.sql.Timestamp;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CropListResDto {

    private List<CropDto> CropList;
    @Setter

    private CropDto cropDto;

    private static class CropDto {

        private Long id;

        private String address;

        private Timestamp regDt;

        private Timestamp updDt;

        private Integer cooltime;

        private Integer rowNum;

        private Integer colNum;

        private String Type;

    }


}
