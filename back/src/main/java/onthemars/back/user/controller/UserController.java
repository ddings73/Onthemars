package onthemars.back.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import onthemars.back.user.dto.request.MemberRegisterRequestDto;
import onthemars.back.user.dto.response.LoginResponseDto;
import onthemars.back.user.dto.response.ProfileResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

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

    @Operation(summary = "마이페이지 조회", description = "address값을 이용하여 회원정보를 조회할 수 있다", tags = {"user-controller"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/{address}")
    public ResponseEntity<ProfileResponseDto> findMemberProfile(@PathVariable String address){
        return ResponseEntity.ok(
            new ProfileResponseDto("addresss", "nickname", "profile.png", LocalDateTime.now())
        );
    }

    @Operation(summary = "마이페이지 수집목록 조회", description = "회원의 address값을 이용하여 수집한 NFT 목록을 조회할 수 있다", tags = {"user-controller"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/{address}/collected")
    public ResponseEntity findCollectList(@PathVariable String address,
        @PageableDefault(size = 10, sort = "address", direction = Direction.DESC) Pageable pageable){
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "마이페이지 민팅목록 조회", description = "회원의 address값을 이용하여 민팅(최초 등록) NFT 목록을 조회할 수 있다", tags = {"user-controller"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/{address}/minted")
    public ResponseEntity findMintList(@PathVariable String address,
        @PageableDefault(size = 10, sort = "address", direction = Direction.DESC) Pageable pageable){
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "마이페이지 즐찾목록 조회", description = "회원의 address값을 이용하여 즐겨찾기한 NFT 목록을 조회할 수 있다", tags = {"user-controller"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/{address}/favorited")
    public ResponseEntity findFavoriteList(@PathVariable String address,
        @PageableDefault(size = 10, sort = "address", direction = Direction.DESC) Pageable pageable){
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "마이페이지 활동목록 조회", description = "회원의 address값을 이용하여 활동한 기록을 조회할 수 있다", tags = {"user-controller"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/{address}/activity")
    public ResponseEntity findActivityList(@PathVariable String address,
        @PageableDefault(size = 10, sort = "address", direction = Direction.DESC) Pageable pageable){
        return ResponseEntity.ok("테스트");
    }

    @Operation(summary = "마이페이지 조합목록 조회", description = "현재 회원의 조합가능한 NFT 목록을 조회할 수 있다", tags = {"user-controller"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "401", description = "Unauthorized(로그인 안함)"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/{address}/combination")
    public ResponseEntity findCombinationList(@PathVariable String address,
        @RequestParam String[] TRCList,
        @PageableDefault(size = 8, sort = "address", direction = Direction.DESC) Pageable pageable){
        return ResponseEntity.ok().build();
    }
    @Operation(summary = "프로필 사진 업로드", description = "프로필 사진을 업로드할 수 있다", tags = {"user-controller"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "401", description = "Unauthorized(로그인 안함)"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping("/profileimg")
    public ResponseEntity uploadProfile(@RequestPart MultipartFile profileImgFile){
        return ResponseEntity.ok("요청은 성공! 하지만 다음기회에~");
    }

    @Operation(summary = "닉네임 변경", description = "회원 닉네임을 변경할 수 있다. 중복불가", tags = {"user-controller"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "401", description = "Unauthorized(로그인 안함)"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping("/nickname")
    public ResponseEntity updateNickname(){
        return ResponseEntity.ok("요청은 성공! 하지만 다음기회에~");
    }
}
