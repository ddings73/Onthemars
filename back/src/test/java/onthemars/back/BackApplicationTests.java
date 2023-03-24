package onthemars.back;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ActiveProfilesResolver;

@SpringBootTest
@ActiveProfiles(resolver = SpringActiveProfilesResolver.class)
class BackApplicationTests {

	@Test
	void contextLoads() {
	}

}
