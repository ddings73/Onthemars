package onthemars.back;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeployController {
    @GetMapping("check")
    public String check(){
        return "check!!!!!!";
    }
}
