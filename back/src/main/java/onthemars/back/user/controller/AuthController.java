package onthemars.back.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.HashMap;
import java.util.Map;
import onthemars.back.user.dto.request.MemberRegisterRequestDto;
import onthemars.back.user.dto.response.LoginResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class AuthController {

    @Operation(summary = "회원가입", description = "MetaMask를 이용하여 회원가입할 수 있다", tags = {"user-controller"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "409", description = "conflict(닉네임 중복)"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping("/signup")
    public ResponseEntity registerMember(@ModelAttribute MemberRegisterRequestDto request){
        return ResponseEntity.ok("회원가입 성공");
    }

    @Operation(summary = "로그인", description = "MetaMask를 이용하여 로그인할 수 있다", tags = {"user-controller"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody String address){
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders -> {
            httpHeaders.add("AccessToken", "Bearer tokentokentokentokentokentokentoken");
            httpHeaders.add("RefreshToken", "Bearer TokenToken");
        }).body(
            new LoginResponseDto("닉네임", "default.png")
        );
    }

    @Operation(summary = "로그아웃", description = "로그아웃을 할 수 있다", tags = {"user-controller"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping("/login")
    public ResponseEntity logout(){
        return ResponseEntity.ok("로그아웃 완료");
    }


    @PostMapping("/nonce/{address}")
    public ResponseEntity loginTest(@PathVariable String address){
        double nonce = Math.floor(Math.random() * 1_000_000);

        Map<String, String> map = new HashMap<>();
        map.put("publicAddress", address);
        map.put("nonce", String.valueOf(nonce));
        return ResponseEntity.ok(map);
    }


    @PostMapping("/t1")
    public ResponseEntity handleSignup(@RequestBody String publicAddress){
        System.out.println(publicAddress);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/auth")
    public ResponseEntity handleAuthenticate(@RequestBody Map<String, String> map){
        return ResponseEntity.ok().build();
    }
}
