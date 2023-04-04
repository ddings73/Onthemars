package onthemars.back.nft.entity;

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
import onthemars.back.user.domain.Profile;
import org.hibernate.annotations.DynamicInsert;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Getter
@Entity
@DynamicInsert
@Table(name = "nft_history")
public class NftHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "transaction_id", nullable = false)
    @ToString.Exclude
    private @NotNull Transaction transaction;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "seller_id", nullable = true, columnDefinition = "char")
    @ToString.Exclude
    private Profile seller;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "buyer_id", nullable = true, columnDefinition = "char")
    @ToString.Exclude
    private Profile buyer;

    @Column(nullable = false)
    private @NotNull Double price;

    @Column(nullable = false)
    private @NotNull LocalDateTime regDt;

    @Column(nullable = false)
    private @NotNull String eventType;

    public NftHistory(
        Transaction transaction,
        Profile seller,
        Profile buyer,
        Double price,
        String eventType
    ) {
        this.transaction = transaction;
        this.seller = seller;
        this.buyer = buyer;
        this.price = price;
        this.regDt = LocalDateTime.now();
        this.eventType = eventType;
    }

}
