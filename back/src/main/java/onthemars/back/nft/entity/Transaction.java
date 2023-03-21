package onthemars.back.nft.entity;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;


@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@DynamicInsert
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @NotNull Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "address", nullable = false)
    private @NotNull Nft nft;

    private Double price;

    @Column(nullable = false)
    private @NotNull Boolean activated;

    @Column(nullable = false)
    private @NotNull LocalDateTime regDt;

    @Column(nullable = false)
    private @NotNull Integer viewCnt;

    public Optional<Double> getPrice() {
        return Optional.ofNullable(price);
    }
}
