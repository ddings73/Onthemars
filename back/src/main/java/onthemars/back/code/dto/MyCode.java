package onthemars.back.code.dto;

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
public class MyCode {

    private @NotNull String name;

    public static MyCode create(Code code) {
        return new MyCode(code.getName());
    }
}
