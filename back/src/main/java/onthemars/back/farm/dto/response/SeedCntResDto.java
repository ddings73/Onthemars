package onthemars.back.farm.dto.response;

import lombok.Getter;

@Getter
public class SeedCntResDto {

    private Integer seedCnt;

    public SeedCntResDto(Integer seedCnt) {
        this.seedCnt = seedCnt;
    }

}
