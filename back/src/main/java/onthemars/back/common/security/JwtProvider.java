package onthemars.back.common.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onthemars.back.user.app.TokenInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component @Slf4j
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secretKey;
    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "Bearer ";
    private final long accessPeriod = 1000L * 60L * 60L * 24L; // 1일
    private final long refreshPeriod = 1000L * 60L * 60L * 24L; // 1일


    private final RedisTemplate<String, String> redisTemplate;

    @PostConstruct
    protected void init(){
        this.secretKey = Base64.getEncoder().encodeToString(this.secretKey.getBytes());
    }

    public TokenInfo generateToken(Authentication authentication) {
        // 권한 가져오기
        String authorities  = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

        String address = authentication.getName();
        return createToken(address, authorities);
    }


    public TokenInfo createToken(String address, String authorities){

        Date now = new Date();
        String accessToken = Jwts.builder()
            .setSubject(address)
            .claim(AUTHORITIES_KEY, authorities) // 권한
            .setExpiration(new Date(now.getTime() + accessPeriod)) // 만료기간
            .signWith(SignatureAlgorithm.HS512, secretKey) // 서명
            .compact();
        String refreshToken = Jwts.builder()
            .setSubject(address)
            .setExpiration(new Date(now.getTime() + refreshPeriod)) // 만료기간
            .signWith(SignatureAlgorithm.HS512, secretKey) // 서명
            .compact();

        redisTemplate.opsForValue().set(refreshToken, address);
        redisTemplate.expire(refreshToken, refreshPeriod, TimeUnit.MILLISECONDS);
        return TokenInfo.of(BEARER_TYPE, accessToken, refreshToken);
    }


    // JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
    public Authentication getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new JwtException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
            Arrays.stream(claims.get("auth").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // UserDetails 객체를 만들어서 Authentication 리턴
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }


    public boolean validateToken(String token) {
        if(token == null) return false;
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    // claim 파싱
    private Claims parseClaims(String token){
        return Jwts.parserBuilder()
            .setSigningKey(secretKey).build()
            .parseClaimsJws(token)
            .getBody();
    }
}