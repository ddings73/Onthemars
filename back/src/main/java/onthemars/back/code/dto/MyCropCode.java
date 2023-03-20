package onthemars.back.code.dto;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import onthemars.back.code.domain.CropCode;

@Getter
@ToString(callSuper = true)
@NoArgsConstructor
public class MyCropCode extends MyCode{
    private @NotNull String bio;
    private @NotNull String plural;

    public MyCropCode(String name, String bio, String plural){
        super(name);
        this.bio = bio;
        this.plural = plural;
    }
    public static MyCropCode create(CropCode code){
        return new MyCropCode(code.getName(), code.getBio(), code.getPlural());
    }
}
