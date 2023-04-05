package onthemars.back.farm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onthemars.back.farm.dto.request.StoreReqDto;
import onthemars.back.farm.dto.response.FarmImgResDto;
import onthemars.back.farm.dto.response.LoadResDto;
import onthemars.back.farm.dto.response.MintResDto;
import onthemars.back.farm.service.FarmService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/farm")
public class FarmController {

    private final FarmService farmService;

    @Operation(summary = "농장 전체의 상태 조회", description = "농장 전체의 상태를 얻는다.", tags = {
        "farm-controller"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "401", description = "Unauthorized(로그인 안함)"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/{address}")
    private ResponseEntity<LoadResDto> findFarm(@PathVariable("address") String address) {
        log.info("findFarm - Call");
        LoadResDto loadResDto = farmService.findFarm(address);
        return ResponseEntity.ok().body(loadResDto);
    }

    @Operation(summary = "농장 전체의 상태 저장", description = "농장 전체의 상태를 저장한다.", tags = {
        "farm-controller"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "401", description = "Unauthorized(로그인 안함)"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping(value = "/save")
    private ResponseEntity updateFarm(@ModelAttribute StoreReqDto storeReqDto) {
        log.info("updateFarm - Call");
        farmService.updateFarm(storeReqDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "랜덤농장주소 조회", description = "랜덤으로 농장 주소를 얻는다.", tags = {"farm-controller"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "401", description = "Unauthorized(로그인 안함)"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/random")
    private ResponseEntity<Map<String, String>> findRandomFarm() {
        log.info("findRandomFarm - Call");
        Map<String, String> map = new HashMap<>();
        map.put("address", farmService.findRandomFarm());
        return ResponseEntity.ok().body(map);
    }

    @Operation(summary = "1단계 nft 데이터 조회", description = "NFT 발급을 위한 데이터를 받는다.", tags = {
        "farm-controller"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "401", description = "Unauthorized(로그인 안함)"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/mint/{dna}")
    private ResponseEntity<MintResDto> findImgUrl(@PathVariable String dna) {
        log.info("findImgUrl - Call");
        MintResDto mintResDto = farmService.findImgUrl(dna);
        return ResponseEntity.ok().body(mintResDto);
    }

    @Operation(summary = "NFT 농장 전시", description = "NFT 농장 전시을 위한 데이터를 받는다.", tags = {
        "farm-controller"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "401", description = "Unauthorized(로그인 안함)"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/farm/nft/{address}")
    private ResponseEntity<FarmImgResDto> findFarmImgUrl(@PathVariable String address) {
        log.info("findFarmImgUrl - Call");
        FarmImgResDto farmImgResDto = farmService.findFarmImgUrl(address);
        return ResponseEntity.ok().body(farmImgResDto);
    }
}