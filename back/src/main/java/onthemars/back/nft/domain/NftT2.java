package onthemars.back.nft.domain;

import com.sun.istack.NotNull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "nft_t2")
public class NftT2 extends Nft {

    @Column(nullable = false)
    private @NotNull String eyes;

    @Column(nullable = false)
    private @NotNull String mouth;

    @Column(nullable = false)
    private @NotNull String headgear;
}
