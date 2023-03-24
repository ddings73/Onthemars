package onthemars.back.code.app;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import onthemars.back.code.domain.Code;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MyCode<T extends Code> {

    private @NotNull String name;
    private @NotNull String type;

    public MyCode create(T code) {
        return new MyCode(code.getName(), code.getType());
    }
}
