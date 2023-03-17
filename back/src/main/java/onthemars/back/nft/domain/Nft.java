package onthemars.back.nft.domain;

import com.sun.istack.NotNull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import onthemars.back.user.domain.User;
import org.hibernate.annotations.DynamicInsert;

@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@DynamicInsert
@Table(name = "nft")
public class Nft {

    @Id
    @Column(nullable = false, unique = true)
    private @NotNull String address;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_address", nullable = false)
    @ToString.Exclude
    private @NotNull User user;

    @Column(nullable = false)
    private @NotNull String tokenId;

    @Column(nullable = false)
    private @NotNull Integer type;

    @Column(nullable = false)
    private @NotNull Integer bg;

    @Column(nullable = false)
    private @NotNull Integer tier;

    @Column(nullable = false)
    private @NotNull String dna;

    @Column(nullable = false)
    private @NotNull Boolean activated;

}
