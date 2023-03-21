package onthemars.back.code.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onthemars.back.code.domain.Code;
import onthemars.back.code.dto.MyCode;
import onthemars.back.code.dto.MyCodeFactory;
import onthemars.back.code.dto.MyColorCode;
import onthemars.back.code.dto.MyCropCode;
import onthemars.back.code.repository.CodeRepository;
import onthemars.back.code.repository.ColorCodeRepository;
import onthemars.back.code.repository.CropCodeRepository;
import org.springframework.stereotype.Service;

@Service @Slf4j
@RequiredArgsConstructor
public class CodeServiceImpl implements CodeService {

    private Map<String, MyCode> codeMap = new ConcurrentHashMap<>();

    private final CodeRepository codeRepository;
    private final CropCodeRepository cropCodeRepository;
    private final ColorCodeRepository colorCodeRepository;

    @PostConstruct
    private void init() {
        codeMap = codeRepository.findAll().stream().collect(
            Collectors.toConcurrentMap(code -> code.getId(), code -> MyCodeFactory.create(new MyCode<>(), code)));
        cropCodeRepository.findAll().forEach(code -> codeMap.replace(code.getId(), MyCodeFactory.create(new MyCropCode(), code)));
        colorCodeRepository.findAll().forEach(code -> codeMap.replace(code.getId(), MyCodeFactory.create(new MyColorCode(), code)));

        log.info("공통코드 생성 완료");
        codeMap.forEach((k, v) -> log.info("{} : {}", k, v));
    }

}