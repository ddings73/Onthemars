package onthemars.back.code.controller;

import lombok.RequiredArgsConstructor;
import onthemars.back.code.dto.response.CodeListResDto;
import org.springframework.http.ResponseEntity;
import onthemars.back.code.service.CodeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/code")
@RequiredArgsConstructor
public class CodeController {
    private final CodeService codeService;

    @PostMapping("/nft")
    public ResponseEntity<CodeListResDto> findNFTCategoryList() {
        CodeListResDto response = codeService.findCodeList();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/game")
    public ResponseEntity findCropStatusList(){
        return ResponseEntity.ok().build();
    }
}
