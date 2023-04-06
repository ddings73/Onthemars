package onthemars.back.code.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onthemars.back.code.app.CodeListItem;
import onthemars.back.code.app.CodeType;
import onthemars.back.code.app.MyCode;
import onthemars.back.code.app.MyCodeFactory;
import onthemars.back.code.app.MyCropCode;
import onthemars.back.code.domain.Code;
import onthemars.back.code.dto.response.CodeListResDto;
import onthemars.back.code.repository.CodeRepository;
import onthemars.back.code.repository.CropCodeRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CodeService {
    private Map<String, MyCode> codeMap;
    private final Map<String, String> typeMap = new ConcurrentHashMap<>();
    private final List<List<CodeListItem>> codeSuperList = new ArrayList<>();

    private final CodeRepository codeRepository;
    private final CropCodeRepository cropCodeRepository;

    @PostConstruct
    private void init() {
        codeMap = codeRepository.findAll().stream().collect(
                Collectors.toConcurrentMap(Code::getId,
                        code -> MyCodeFactory.create(new MyCode<>(), code)));
        cropCodeRepository.findAll().forEach(
                code -> codeMap.replace(code.getId(), MyCodeFactory.create(new MyCropCode(), code)));

        log.info("공통코드 생성 완료");
        codeMap.keySet().forEach(k -> {
            String prefix = k.substring(0, 3);
            typeMap.put(codeMap.get(k).getType(), prefix);
        });

        typeMap.forEach((k, v) -> {
            log.info("type {} => prefix {}", k, v);
        });

        typeMap.forEach((type, prefix) -> {
            List<CodeListItem> list = new ArrayList<>();
            codeMap.forEach((k, v) -> {
                if (k.startsWith(prefix)) {
                    list.add(CodeListItem.of(k, v));
                }
            });
            Collections.sort(list, Comparator.comparing(CodeListItem::getCode));
            codeSuperList.add(list);
        });
    }

    public CodeListResDto findCodeList() {
        return CodeListResDto.of(codeMap, typeMap);
    }

    public String randDomCode() {
        String cropPrefix = typeMap.get(CodeType.CROP.name());
        int cropLength = 0;
        for (String key : codeMap.keySet()) {
            if (key.startsWith(cropPrefix)) {
                cropLength++;
            }
        }
        int randIdx = (int) (Math.random() * cropLength) + 1;
        return cropPrefix + String.format("%02d", randIdx);
    }

    public <T extends MyCode> T getCode(Class<T> clazz, String id) {
        return id != null && codeMap.containsKey(id)
                ? clazz.cast(codeMap.get(id))
                : null; // exception 처리 필요
    }

    public Optional<List<CodeListItem>> getTransactionList() {
        String transactionPrefix = typeMap.get(CodeType.TRANSACTION.name());
        for (List<CodeListItem> codeListItems : codeSuperList) {
            String code = codeListItems.get(0).getCode();
            if (code.startsWith(transactionPrefix)) {
                return Optional.of(codeListItems);
            }
        }

        return Optional.ofNullable(null);
    }


    public Optional<String> AttrToId(String attrName){
        Set<String> keys = codeMap.keySet();

        attrName = attrName.toUpperCase();
        for (String key : keys) {
            MyCode myCode = codeMap.get(key);
            if(myCode.getName().equals(attrName)){
                return Optional.of(key);
            }
        }

        return Optional.empty();
    }

}