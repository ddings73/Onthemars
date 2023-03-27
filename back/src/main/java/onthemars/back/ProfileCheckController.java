package onthemars.back;

import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProfileCheckController {
    private final Environment env;

    @PostMapping("/profile")
    public String getProfile(){
        String[] profiles = env.getActiveProfiles();
        return profiles.length == 2 ? profiles[1] : "";
    }
}