package onthemars.back.code.dto;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import onthemars.back.code.domain.ColorCode;

@Getter
@ToString(callSuper = true)
@NoArgsConstructor
public class MyColorCode extends MyCode{
    private @NotNull String hexCode;

    public MyColorCode(String name, String hexCode){
        super(name);
        this.hexCode = hexCode;
    }

    public static MyColorCode create(ColorCode code){
        return new MyColorCode(code.getName(), code.getHexCode());
    }
}
