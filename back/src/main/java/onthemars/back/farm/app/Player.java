package onthemars.back.farm.app;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import onthemars.back.farm.domain.SeedHistory;
import onthemars.back.user.domain.Member;
import onthemars.back.user.domain.Profile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Player {

    private String address;

    private String nickname;

    private Integer buySeedCnt;

    private Double buySeedPrice;

    private HarvestList harvestList;

    public SeedHistory setSeedHistory(Member member) {
        return SeedHistory.builder()
            .regDt(LocalDateTime.now())
            .price(this.buySeedPrice)
            .member(member)
            .cnt(this.buySeedCnt)
            .build();
    }

    public static Player of(Profile profile){
        return Player.builder()
            .address(profile.getAddress())
            .buySeedCnt(0)
            .nickname(profile.getNickname())
            .buySeedPrice(0.0)
            .harvestList(new HarvestList())
            .build();
    }
}
