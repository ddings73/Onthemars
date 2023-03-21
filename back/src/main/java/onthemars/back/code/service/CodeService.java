package onthemars.back.code.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onthemars.back.code.MyCode;
import onthemars.back.code.MyCodeFactory;
import onthemars.back.code.MyCropCode;
import onthemars.back.code.dto.response.CodeListResDto;
import onthemars.back.code.repository.CodeRepository;
import onthemars.back.code.repository.CropCodeRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CodeService {

    private Map<String, MyCode> codeMap = new ConcurrentHashMap<>();
    private Map<String, String> typeMap = new ConcurrentHashMap<>();

    private final CodeRepository codeRepository;
    private final CropCodeRepository cropCodeRepository;

    @PostConstruct
    private void init() {
        codeMap = codeRepository.findAll().stream().collect(
            Collectors.toConcurrentMap(code -> code.getId(),
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
    }

    public CodeListResDto findCodeList() {
        return CodeListResDto.of(codeMap, typeMap);
    }
}