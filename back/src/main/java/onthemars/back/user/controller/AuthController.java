package onthemars.back.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onthemars.back.user.dto.request.AuthRequestDto;
import onthemars.back.user.dto.request.MemberRegisterRequestDto;
import onthemars.back.user.dto.response.JwtResponseDto;
import onthemars.back.user.dto.response.LoginResponseDto;
import onthemars.back.user.dto.response.NonceResponseDto;
import onthemars.back.user.service.AuthService;
import onthemars.back.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController @Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final String ADDRESS = "address";
    private final AuthService authService;

    @Operation(summary = "회원가입", description = "MetaMask를 이용하여 회원가입할 수 있다", tags = {"auth-controller"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "409", description = "conflict(닉네임 중복)"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping("/signup")
    public ResponseEntity registerMember(@ModelAttribute MemberRegisterRequestDto request) {
        authService.registerUser(request);
        return ResponseEntity.ok("회원가입 성공");
    }

    @Operation(summary = "로그인", description = "존재하는 회원인지 확인한 다음 랜덤한 nonce 값을 내려준다", tags = {"auth-controller"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping("/login")
    public ResponseEntity<NonceResponseDto> login(@RequestBody Map<String, String> map){
        String address = map.get(ADDRESS);
        NonceResponseDto response = authService.loginUser(address);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "사용자 인증", description = "Metamask의 서명값을 검증하여 로그인을 승인한다", tags = {"auth-controller"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "401", description = "UnAuthorization"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping("/auth")
    public ResponseEntity<LoginResponseDto> auth(@RequestBody AuthRequestDto requestDto){
        JwtResponseDto jwtResponse = authService.authUser(requestDto);
        return ResponseEntity.ok().headers(httpHeaders -> {
            httpHeaders.add("accessToken", jwtResponse.getAccessToken());
            httpHeaders.add("refreshToken", jwtResponse.getRefreshToken());
        }).body(LoginResponseDto.of(jwtResponse));
    }


    @Operation(summary = "로그아웃", description = "로그아웃을 할 수 있다", tags = {"auth-controller"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping("/login")
    public ResponseEntity logout(){
        authService.logOut();
        return ResponseEntity.ok("로그아웃 완료");
    }


}
