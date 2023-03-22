package onthemars.back.code.controller;

import lombok.RequiredArgsConstructor;
import onthemars.back.code.dto.response.CodeListResDto;
import onthemars.back.code.service.CodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CodeController {
    private final CodeService codeService;
    @PostMapping("/code/nft")
    public ResponseEntity<CodeListResDto> findNFTCategoryList() {
        CodeListResDto response = codeService.findCodeList();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/code/nft-activity")
    public ResponseEntity findNFTHistoryList(){
        return ResponseEntity.ok().build();
    }

    @PostMapping("/code/game")
    public ResponseEntity findCropStatusList(){
        return ResponseEntity.ok().build();
    }
}
