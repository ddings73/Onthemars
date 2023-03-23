package onthemars.back.code.app;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.StringUtils;


@Getter
@AllArgsConstructor
public class CodeListItem {
    private @NotNull String code;
    private @NotNull String name;

    public static CodeListItem of(String id, MyCode code) {
        String codeName = code.getName();
        return new CodeListItem(id, StringUtils.capitalize(codeName.toLowerCase()));
    }
}
