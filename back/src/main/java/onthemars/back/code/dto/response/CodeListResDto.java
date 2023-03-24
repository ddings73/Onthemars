package onthemars.back.code.dto.response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import onthemars.back.code.app.CodeListItem;
import onthemars.back.code.app.MyCode;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CodeListResDto {

    private List<Map<String, List<CodeListItem>>> codeList;

    public static CodeListResDto of(Map<String, MyCode> codeMap, Map<String, String> typeMap) {
        List<Map<String, List<CodeListItem>>> list = new ArrayList<>();
        typeMap.forEach((k, v)->{
            Map<String, List<CodeListItem>> map = new HashMap<>();
            map.put(k, listOf(codeMap, v));
            list.add(map);
        });

        return new CodeListResDto(list);
    }

    private static List<CodeListItem> listOf(Map<String, MyCode> codeMap, String prefix){
        List<CodeListItem> list = new ArrayList<>();
        codeMap.forEach((k,v)->{
            if(k.startsWith(prefix)){
                list.add(CodeListItem.of(k,v));
            }
        });
        Collections.sort(list, Comparator.comparing(CodeListItem::getCode));
        return list;
    }
}
