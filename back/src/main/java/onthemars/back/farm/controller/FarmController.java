package onthemars.back.farm.controller;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onthemars.back.farm.dto.request.RegisterSeedHistoryReqDto;
import onthemars.back.farm.service.FarmService;
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


    @PostMapping("/seed")
    private ResponseEntity registerSeedHistory(
        @RequestBody RegisterSeedHistoryReqDto registerSeedHistoryReqDto) {
        log.info("registerSeedHistory - Call");
        farmService.registerSeedHistory(registerSeedHistoryReqDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/inventory")
    private ResponseEntity countSeed() {
        log.info("findCountSeed - Call");
        Map<String, Integer> map = new HashMap<>();
        map.put("seedCnt", 2);
        // 더미 반환
        farmService.countSeed();
        return ResponseEntity.ok().body(farmService.countSeed());
    }

    @GetMapping("/")
    private ResponseEntity findAllCrop(@PathVariable("address") String address) {
        log.info("findAllCrop - Call");
        return ResponseEntity.ok().body(farmService.findAllCrop());
    }

    @PutMapping("/growth")
    private ResponseEntity updateCrop() {
        log.info("updateCrop - Call");
        Map<String, String> map = new HashMap<>();
        map.put("result", "success");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/growth")
    private ResponseEntity updateSeed() {
        log.info("updateSeed - Call");
        Map<String, String> map = new HashMap<>();
        map.put("result", "success");
        return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
    }


    @PutMapping("/harvest")
    private ResponseEntity registerCrop() {
        log.info("registerCrop - Call");
        Map<String, String> map = new HashMap<>();
        map.put("result", "success");
        return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
    }

    @PutMapping("/character")
    private ResponseEntity updateCharacter() {
        log.info("updateCharacter - Call");
        Map<String, String> map = new HashMap<>();
        map.put("result", "success");
        return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
    }

    @GetMapping("/nft")
    private ResponseEntity findNFT() {
        log.info("findNFT - Call");
        Map<String, String> map = new HashMap<>();
        map.put("result", "success");
        return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
    }

}