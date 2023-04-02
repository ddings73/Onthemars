package onthemars.back.farm.domain;

import com.sun.istack.NotNull;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import onthemars.back.farm.app.CropList.CropDto;
import onthemars.back.user.domain.Member;
import org.hibernate.annotations.DynamicInsert;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@DynamicInsert
@ToString
@Table(name = "crop")
public class Crop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_address", nullable = false, columnDefinition = "char")
    @ToString.Exclude
    private @NotNull Member member;

    @Column(nullable = false)
    private @NotNull String state;

    @Column(nullable = false)
    private @NotNull LocalDateTime regDt;

    @Column(nullable = false)
    private @NotNull LocalDateTime updDt;

    @Column(nullable = false)
    private @NotNull Integer cooltime;

    private Integer potNum;
    @Column(nullable = false)
    private @NotNull String type;

    @Column(nullable = false)
    private @NotNull Boolean isWatered;


    public void updateCrop(CropDto cropDto) {
        this.state = cropDto.getState();
        this.updDt = LocalDateTime.now();
        this.cooltime = cropDto.getCooltime();
        this.potNum = cropDto.getPotNum();
        this.type = cropDto.getType();
        this.isWatered = cropDto.getIsWaterd();
    }

    public void setPotNumNull() {
        this.potNum = null;
    }
}
