package onthemars.back.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onthemars.back.user.dto.request.AuthRequestDto;
import onthemars.back.user.dto.request.LoginRequestDto;
import onthemars.back.user.dto.request.MemberRegisterRequestDto;
import onthemars.back.user.dto.response.JwtResponseDto;
import onthemars.back.user.dto.response.LoginResponseDto;
import onthemars.back.user.dto.response.NonceResponseDto;
import onthemars.back.user.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입", description = "MetaMask를 이용하여 회원가입할 수 있다", tags = {
        "auth-controller"})
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

    @Operation(summary = "로그인", description = "존재하는 회원인지 확인한 다음 랜덤한 nonce 값을 내려준다", tags = {
        "auth-controller"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping("/login")
    public ResponseEntity<NonceResponseDto> login(@RequestBody LoginRequestDto requestDto) {
        NonceResponseDto response = authService.loginUser(requestDto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "사용자 인증", description = "Metamask의 서명값을 검증하여 로그인을 승인한다", tags = {
        "auth-controller"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "401", description = "UnAuthorization"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping
    public ResponseEntity<LoginResponseDto> auth(@RequestBody AuthRequestDto requestDto) {
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
    public ResponseEntity logout(@RequestHeader String refreshToken) {
        authService.logoutUser(refreshToken);
        return ResponseEntity.ok("로그아웃 완료");
    }

    @Operation(summary = "토큰 재발급", description = "JWT 재발급 요청")
    @PostMapping("/token/{address}")
    public ResponseEntity reissueToken(@RequestHeader String accessToken, @RequestHeader String refreshToken){
        JwtResponseDto jwtResponse = authService.reissueToken(accessToken, refreshToken);

        return ResponseEntity.ok().headers(httpHeaders -> {
            httpHeaders.add("accessToken", jwtResponse.getAccessToken());
            httpHeaders.add("refreshToken", jwtResponse.getRefreshToken());
        }).build();
    }

}
