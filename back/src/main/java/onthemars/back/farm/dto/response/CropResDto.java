package onthemars.back.farm.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import onthemars.back.farm.domain.Crop;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CropResDto {

    private List<CropDto> crops;

    public static CropResDto of(List<Crop> cropList) {
        List<CropDto> crops = cropList.stream().map(CropDto::make).collect(Collectors.toList());
        CropResDto cropResDto = new CropResDto(crops);
        return cropResDto;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    private static class CropDto {

        private Long cropId;

        private String state;

        private Boolean growth;

        private Integer rowNum;

        private Integer colNum;

        private String type;

        // builder
        public static CropDto make(Crop crop) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime compare = crop.getUpdDt().plusSeconds(crop.getCooltime());
            return CropDto.builder()
                .cropId(crop.getId())
                .state(crop.getState())
                .growth(now.isAfter(compare))
                .type(crop.getType())
                .build();
        }
    }


}
