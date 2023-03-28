package onthemars.back.nft.entity;

import com.sun.istack.NotNull;
import java.time.LocalDateTime;
import java.util.Optional;
import javax.persistence.*;

import lombok.*;
import onthemars.back.user.domain.Member;
import onthemars.back.user.domain.Profile;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_address", nullable = false)
    private @NotNull Profile member;

    @Column(nullable = false)
    private @NotNull String contractAddress;

    @Column(nullable = false)
    private @NotNull String tokenId;

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

}
