package onthemars.back.code.app;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import onthemars.back.code.domain.CropCode;

@Getter
@ToString(callSuper = true)
@NoArgsConstructor(force = true)
public class MyCropCode extends MyCode<CropCode>{
    private @NotNull String bio;
    private @NotNull String plural;
    private @NotNull String banner;

    public MyCropCode(String name, String type, String bio, String plural, String path, String banner){
        super(name, type, path);
        this.bio = bio;
        this.plural = plural;
        this.banner = banner;
    }

    @Override
    public MyCode create(CropCode code) {
        return new MyCropCode(code.getName(), code.getType(), code.getBio(), code.getPlural(), code.getPath(), code.getBanner());
    }
}
