package onthemars.back.farm.app;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import onthemars.back.farm.domain.Crop;
import onthemars.back.user.domain.Member;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CropDto {


    private Long cropId;

    private String type;

    private String state;

    private Integer potNum;

    private Integer cooltime;

    private Boolean isWaterd;

    private Boolean isPlanted;

    private Boolean isTimeDone;


    // builder
    public static CropDto of(Crop crop) {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime compare = crop.getUpdDt().plusSeconds(crop.getCooltime());

        Boolean isTimeDone = new Boolean(false);
        if (now.isAfter(compare)) {
            isTimeDone = true;
        } else {
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

    public static CropDto makeNull(Integer potNum) {
        return CropDto.builder()
            .cropId((long)0)
            .type(null)
            .state(null)
            .potNum(potNum)
            .cooltime(null)
            .isWaterd(null)
            .isPlanted(false)
            .isTimeDone(null)
            .build();
    }

    public Crop toCrop(Member member) {
        return Crop.builder()
            .type(this.type)
            .regDt(LocalDateTime.now())
            .updDt(LocalDateTime.now())
            .cooltime(this.cooltime)
            .isWatered(this.isWaterd)
            .state(this.state)
            .member(member)
            .build();
    }


}
