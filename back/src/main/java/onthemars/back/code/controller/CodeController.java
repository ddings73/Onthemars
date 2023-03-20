package onthemars.back.code.controller;

import lombok.RequiredArgsConstructor;
import onthemars.back.code.service.CodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CodeController {
    private final CodeService codeService;
    @PostMapping("/code/list")
    public ResponseEntity test() {
        return ResponseEntity.ok().build();
    }
}
