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

    private Integer curSeedCnt;

    private List<Harvest> harvests;


    public SeedHistory setSeedHistory(Member member) {
        return SeedHistory.builder()
            .regDt(LocalDateTime.now())
            .price(this.buySeedPrice)
            .member(member)
            .cnt(this.buySeedCnt)
            .build();
    }

    public static Player of(Profile profile) {
        List<Harvest> harvests = new ArrayList<>();
        harvests.add(Harvest.builder().cropId((long)-1).type("CRS01").build());
        return Player.builder()
            .address(profile.getAddress())
            .buySeedCnt(0)
            .curSeedCnt(profile.getSeedCnt())
            .nickname(profile.getNickname())
            .buySeedPrice(0.0)
            .harvests(harvests)
            .build();
    }


}
