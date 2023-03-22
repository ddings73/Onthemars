package onthemars.back.code.domain;

import com.sun.istack.NotNull;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CropCode extends Code {

    @Column(nullable = false)
    private @NotNull String bio;

    @Column(nullable = false)
    private @NotNull String plural;

    @Column(nullable = false)
    private @NotNull String banner;
}
