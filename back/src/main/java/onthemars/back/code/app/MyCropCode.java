package onthemars.back.code.app;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import onthemars.back.code.domain.CropCode;

@Getter
@ToString(callSuper = true)
@NoArgsConstructor
public class MyCropCode extends MyCode<CropCode>{
    private @NotNull String bio;
    private @NotNull String plural;

    public MyCropCode(String name, String type, String bio, String plural){
        super(name, type);
        this.bio = bio;
        this.plural = plural;
    }

    @Override
    public MyCode create(CropCode code) {
        return new MyCropCode(code.getName(), code.getType(), code.getBio(), code.getPlural());
    }
}
