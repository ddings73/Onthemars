package onthemars.back.farm.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onthemars.back.code.dto.MyCode;
import onthemars.back.farm.dto.request.HarvestReqDto;
import onthemars.back.farm.dto.request.PlantReqDto;
import onthemars.back.farm.dto.request.SeedHistoryReqDto;
import onthemars.back.farm.dto.request.WaterReqDto;
import onthemars.back.farm.dto.response.CropResDto;
import onthemars.back.farm.dto.response.SeedCntResDto;
import onthemars.back.farm.service.FarmService;
import onthemars.back.user.domain.Member;
import org.springframework.http.HttpStatus;
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

    LocalDateTime date = LocalDateTime.parse("2023-03-21T17:49:07.000000");
    Member member = new Member("1", date);
    // 위 로직 user Security 나오면 수정

    @PostMapping("/seed")
    private ResponseEntity registerSeedHistory(
        @RequestBody SeedHistoryReqDto seedHistoryReqDto) {
        log.info("registerSeedHistoryController - Call");
        farmService.registerSeedHistory(member, seedHistoryReqDto);
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
        System.out.println(address);
        CropResDto cropListResDto = farmService.findAllCrop(member);
        return ResponseEntity.ok().body(cropListResDto);
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
        MyCode myCode = farmService.updateSeed(member, plantReqDto);
        return ResponseEntity.ok().body(myCode);
    }


    @PutMapping("/harvest")
    private ResponseEntity registerNFT(HarvestReqDto harvestReqDto) {
        log.info("registerCropController - Call");
        Map<String, String> map = new HashMap<>();
        map.put("result", "success");
        farmService.registerNFT(member, harvestReqDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/random")
    private ResponseEntity findRandomFarm() {
        log.info("findRandomFarmController - Call");
        Map<String, String> map = new HashMap<>();
        map.put("address", farmService.findRandomFarm());
        return ResponseEntity.ok().body(map);
    }

    @GetMapping("/nft")
    private ResponseEntity findNFT() {
        log.info("findNFTController - Call");
        Map<String, String> map = new HashMap<>();
        map.put("result", "success");
        return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
    }


}