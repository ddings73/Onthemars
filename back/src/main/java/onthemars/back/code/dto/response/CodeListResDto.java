package onthemars.back.code.dto.response;

import com.sun.istack.NotNull;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import onthemars.back.code.MyCode;
import org.springframework.util.StringUtils;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class CodeListResDto {

    /**
     * 작물종류 - 값 배경 색 - 값 눈 - 값 입 - 값 머리장식 - 값
     * <p>
     * 거래종류 - 값
     * <p>
     * 작물상태 - 값
     */

    public static CodeListResDto of(Map<String, MyCode> codeMap, Map<String, String> typeMap) {

        return null;
    }

    @AllArgsConstructor
    private static class CodeListItem {

        private @NotNull String id;
        private @NotNull String name;

        private static CodeListItem of(String id, MyCode code) {
            String codeName = code.getName();
            return new CodeListItem(id, StringUtils.capitalize(codeName));
        }
    }
}
