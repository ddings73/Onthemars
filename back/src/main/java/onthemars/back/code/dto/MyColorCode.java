package onthemars.back.code.dto;

import com.sun.istack.NotNull;
import java.awt.Color;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import onthemars.back.code.domain.Code;
import onthemars.back.code.domain.ColorCode;

@Getter
@ToString(callSuper = true)
@NoArgsConstructor
public class MyColorCode extends MyCode<ColorCode> {
    private @NotNull String hexCode;

    public MyColorCode(String name, String type, String hexCode){
        super(name, type);
        this.hexCode = hexCode;
    }

    @Override
    public MyCode create(ColorCode code) {
        return new MyColorCode(code.getName(), code.getType(), code.getHexCode());
    }
}
