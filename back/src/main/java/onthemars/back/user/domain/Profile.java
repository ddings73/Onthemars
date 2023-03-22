package onthemars.back.user.domain;

import com.sun.istack.NotNull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@DynamicInsert
@Table(name = "profile")
public class Profile extends Member {

    @Column(nullable = false)
    private @NotNull String nickname;

    @Column(nullable = false)
    private @NotNull String profileImg;

    @Column(nullable = false)
    private @NotNull Integer seedCnt;

}