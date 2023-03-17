package onthemars.back.farm.domain;

import com.sun.istack.NotNull;
import java.time.LocalDateTime;
import java.util.Optional;
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
import lombok.Setter;
import lombok.ToString;
import onthemars.back.user.domain.User;
import org.hibernate.annotations.DynamicInsert;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@DynamicInsert
@Table(name = "crop")
public class Crop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @NotNull Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "address", nullable = false)
    @ToString.Exclude
    private @NotNull User user;

    @Column(nullable = false)
    private @NotNull String state;

    @Column(nullable = false)
    private @NotNull LocalDateTime regDt;

    @Column(nullable = false)
    private @NotNull LocalDateTime updDt;

    @Column(nullable = false)
    private @NotNull Integer cooltime;

    private Integer rowNum;

    private Integer colNum;

    @Column(nullable = false)
    private @NotNull String type;

    public Optional<Integer> getRowNum() {
        return Optional.ofNullable(rowNum);
    }

    public Optional<Integer> getColNum() {
        return Optional.ofNullable(colNum);
    }

}
