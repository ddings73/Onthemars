package onthemars.back.nft.entity;

import com.sun.istack.NotNull;
import lombok.*;
import onthemars.back.user.domain.Profile;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;


@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Getter
@Entity
@DynamicInsert
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @NotNull Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_address", nullable = false, columnDefinition = "char")
    private @NotNull Profile member;

    @Column(nullable = false, columnDefinition = "char")
    private @NotNull String contractAddress;

    @Column(nullable = false)
    private @NotNull Long tokenId;

    @Column(nullable = false)
    private @NotNull Boolean isBurn;

    @Column(nullable = false)
    private @NotNull String dna;

    @Column(nullable = false)
    private @NotNull Double price;

    @Column(nullable = false)
    private @NotNull LocalDateTime regDt;

    @Column(nullable = false)
    private @NotNull Integer viewCnt;

    @Column(nullable = false)
    private @NotNull Boolean isSale;

    @Column(nullable = false)
    private @NotNull String imgUrl;

    public void updateTransaction(Profile owner, Double price, Boolean isSale) {
        this.member = owner;
        this.price = price;
        this.isSale = isSale;
    }
}
