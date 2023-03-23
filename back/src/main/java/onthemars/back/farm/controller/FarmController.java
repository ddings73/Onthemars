package onthemars.back.farm.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onthemars.back.farm.dto.request.HarvestReqDto;
import onthemars.back.farm.dto.request.PlantReqDto;
import onthemars.back.farm.dto.request.SeedHistoryReqDto;
import onthemars.back.farm.dto.request.WaterReqDto;
import onthemars.back.farm.dto.response.SeedCntResDto;
import onthemars.back.farm.service.FarmService;
import onthemars.back.user.domain.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/farm")
public class FarmController {

    private final FarmService farmService;

    LocalDateTime date = LocalDateTime.parse("2023-03-23T14:33:12.000000");
    Member member = new Member("user_address_1", date);
    // 위 로직 user Security 나오면 수정

    @PostMapping("/seed")
    private ResponseEntity buySeed(
        @RequestBody SeedHistoryReqDto seedHistoryReqDto) {
        log.info("registerSeedHistoryController - Call");
        farmService.buySeed(member, seedHistoryReqDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/inventory")
    private ResponseEntity countSeed() {
        log.info("countSeedController - Call");
        SeedCntResDto seedCntResDto = farmService.countSeed(member);
        return ResponseEntity.ok().body(seedCntResDto);
    }

    // in progress
    @GetMapping("/{address}")
    private ResponseEntity findAllCrop(@PathVariable("address") String address) {
        log.info("findAllCropController - Call");
        // 해당 address 로 user 찾는 함수 추가 해야함
//        System.out.println(address);
//        CropResDto cropListResDto = farmService.findAllCrop(member);
        String response = "{\n"
            + "\"crops\": [\n"
            + "{\n"
            + "\"cropId\": 8,\n"
            + "\"state\": \"CRT01\",\n"
            + "\"growth\": true,\n"
            + "\"rowNum\": null,\n"
            + "\"colNum\": null,\n"
            + "\"type\": {\n"
            + "\"code\": \"CRS03\",\n"
            + "\"name\": \"CUCUMBER\",\n"
            + "}\n"
            + "},\n"
            + "{\n"
            + "\"cropId\": 9,\n"
            + "\"state\": \"CRT01\",\n"
            + "\"growth\": true,\n"
            + "\"rowNum\": null,\n"
            + "\"colNum\": null,\n"
            + "\"type\": {\n"
            + "\"code\": \"CRS03\",\n"
            + "\"name\": \"CUCUMBER\",\n"
            + "}\n"
            + "},\n"
            + "{\n"
            + "\"cropId\": 10,\n"
            + "\"state\": \"CRT01\",\n"
            + "\"growth\": true,\n"
            + "\"rowNum\": null,\n"
            + "\"colNum\": null,\n"
            + "\"type\": {\n"
            + "\"code\": \"CRS03\",\n"
            + "\"name\": \"CUCUMBER\",\n"
            + "}\n"
            + "}\n"
            + "]\n"
            + "}";
        return ResponseEntity.ok().body(response);
    }

    // 물 주기
    @PutMapping("/growth")
    private ResponseEntity updateCrop(@RequestBody WaterReqDto waterReqDto) {
        log.info("updateCropController - Call");
        // member 가 해당 권한이 있는지 체크하는 로직 추가해야함
        farmService.updateCrop(waterReqDto);
        return ResponseEntity.ok().build();
    }

    // 씨앗 심기
    @PostMapping("/growth")
    private ResponseEntity updateSeed(@RequestBody PlantReqDto plantReqDto) {
        log.info("updateSeedController - Call");
//        CodeListResDto myCode = farmService.updateSeed(member, plantReqDto);
        String response ="{\n"
            + "\"type\":  {\n"
            + "code: \"CRS02\",\n"
            + "nmae : \"WHEAT\"\n"
            + "}\n"
            + "}";
        return ResponseEntity.ok().body(response);
    }


    // 작물 수확, NFT 민팅
    @PutMapping("/harvest")
    private ResponseEntity registerNFT(HarvestReqDto harvestReqDto) {
        log.info("registerCropController - Call");
        farmService.registerNFT(member, harvestReqDto);
        return ResponseEntity.ok().build();
    }

    // 랜덤으로 사용자 방 입장
    @GetMapping("/random")
    private ResponseEntity findRandomFarm() {
        log.info("findRandomFarmController - Call");
        Map<String, String> map = new HashMap<>();
        map.put("address", farmService.findRandomFarm());
        return ResponseEntity.ok().body(map);
    }

    // nft 전시
    @GetMapping("/nft")
    private ResponseEntity findNFT() {
        log.info("findNFTController - Call");
        return  ResponseEntity.ok().build();
    }


}