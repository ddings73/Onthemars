package onthemars.back.farm.app;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import onthemars.back.farm.domain.Crop;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CropList {

    private List<CropDto> crops;

    public static CropList of(List<Crop> cropList) {
        List<CropDto> crops = new ArrayList<>();

        for (int i = 0; i < 18; i++) { // 화분 수(18개) 맞춰서 list 생성
            if(cropList.isEmpty()){
                crops.add(CropDto.makeNull());
            }
            else {
                if(cropList.get(i).getPotNum().equals(new Integer(i))){
                    crops.add(CropDto.makeLoad(cropList.get(i)));
                }else{
                    crops.add(CropDto.makeNull());
                }
            }
        }
        return  new CropList(crops);
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @ToString
    public static class CropDto {

        private Long cropId;

        private String type;

        private String state;

        private Integer potNum;

        private Integer cooltime;

        private Boolean isWaterd;

        private Boolean isPlanted;

        private Boolean isTimeDone;

        // builder
        public static CropDto makeLoad(Crop crop) {

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime compare = crop.getUpdDt().plusSeconds(crop.getCooltime());

            Boolean isTimeDone = new Boolean(false);
            if(now.isAfter(compare)){
                isTimeDone = true;
            }else{
                isTimeDone = false;
            }
            return CropDto.builder()
                .cropId(crop.getId())
                .type(crop.getType())
                .state(crop.getState())
                .potNum(crop.getPotNum())
                .cooltime(crop.getCooltime())
                .isWaterd(crop.getIsWatered())
                .isPlanted(true)
                .isTimeDone(isTimeDone)
                .build();
        }

        public static CropDto makeNull() {
            return CropDto.builder()
                .cropId(null)
                .type(null)
                .state(null)
                .potNum(null)
                .cooltime(null)
                .isWaterd(null)
                .isPlanted(false)
                .isTimeDone(null)
                .build();
        }
    }


}
