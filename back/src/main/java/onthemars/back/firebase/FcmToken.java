package onthemars.back.firebase;

import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RedisHash("UserToken")
@NoArgsConstructor
@AllArgsConstructor
public class FcmToken {
    @Id
    private String id;

    @Indexed
    private String address;
    private String token;

    public FcmToken(String address, String token){
        this.address = address;
        this.token = token;
    }
}
