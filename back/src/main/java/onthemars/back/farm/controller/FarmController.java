package onthemars.back.farm.controller;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import onthemars.back.farm.service.FarmService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/farm")
public class FarmController {

    private final FarmService farmService;

    @PostMapping("/seed")
    private ResponseEntity registerSeed() {
        System.out.println("registerSeed");
        Map<String, String> map = new HashMap<>();
        map.put("result", "success");
        return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
    }

    @GetMapping("/inventory")
    private ResponseEntity findSeedCnt() {
        System.out.println("findCountSeed");
        Map<String, Integer> map = new HashMap<>();
        map.put("seedCnt", 2);
        return new ResponseEntity<Map<String, Integer>>(map, HttpStatus.OK);
    }

    @GetMapping("/")
    private ResponseEntity findAllCrop(@PathVariable("address") String address) {
        System.out.println("findAllCrop");
        Map<String, String> map = new HashMap<>();
        map.put("result", "success");

        return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
    }

    @PostMapping("/growth")
    private ResponseEntity updateSeed() {
        System.out.println("updateSeed");
        Map<String, String> map = new HashMap<>();
        map.put("result", "success");
        return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
    }

    @PutMapping("/growth")
    private ResponseEntity updateCrop() {
        System.out.println("updateCrop");
        Map<String, String> map = new HashMap<>();
        map.put("result", "success");
        return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
    }

    @PutMapping("/harvest")
    private ResponseEntity registerCrop() {
        System.out.println("registerCrop");
        Map<String, String> map = new HashMap<>();
        map.put("result", "success");
        return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
    }

    @PutMapping("/character")
    private ResponseEntity updateCharacter() {
        System.out.println("updateCharacter");
        Map<String, String> map = new HashMap<>();
        map.put("result", "success");
        return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
    }

    @GetMapping("/nft")
    private ResponseEntity findNFT() {
        System.out.println("findNFT");
        Map<String, String> map = new HashMap<>();
        map.put("result", "success");
        return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
    }

}