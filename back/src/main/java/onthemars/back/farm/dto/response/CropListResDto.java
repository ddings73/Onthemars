package onthemars.back.farm.dto.response;

import java.sql.Timestamp;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class CropListResDto {

    private List<CropDto> CropList;

    @Setter
    @Getter
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
