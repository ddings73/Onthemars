package onthemars.back.farm.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onthemars.back.code.dto.MyCode;
import onthemars.back.code.repository.CodeRepository;
import onthemars.back.farm.domain.Crop;
import onthemars.back.farm.domain.SeedHistory;
import onthemars.back.farm.dto.request.HarvestReqDto;
import onthemars.back.farm.dto.request.PlantReqDto;
import onthemars.back.farm.dto.request.SeedHistoryReqDto;
import onthemars.back.farm.dto.request.WaterReqDto;
import onthemars.back.farm.dto.response.CropResDto;
import onthemars.back.farm.dto.response.SeedCntResDto;
import onthemars.back.farm.repository.CropRepository;
import onthemars.back.farm.repository.SeedHistoryRepository;
import onthemars.back.user.domain.Member;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class FarmService {


    private final CropRepository cropRepository;
    private final SeedHistoryRepository seedHistoryRepository;

    private final CodeRepository codeRepository;

    public void registerSeedHistory(Member member,
        SeedHistoryReqDto seedHistoryReqDto) {

        LocalDateTime now = LocalDateTime.now();

        SeedHistory seedHistory = SeedHistory.builder()
            .member(member)
            .cnt(seedHistoryReqDto.getCnt())
            .price(seedHistoryReqDto.getPrice())
            .regDt(now)
            .build();
        seedHistoryRepository.save(seedHistory);
        // 씨앗 구매 내역 등록
    }

    public SeedCntResDto countSeed(Member member) {
        Long seedCnt = seedHistoryRepository.countByMember(member);
        SeedCntResDto seedCntResDto = new SeedCntResDto(seedCnt);
        return seedCntResDto;
    }

    public CropResDto findAllCrop(Member member) {
        List<Crop> cropList = cropRepository.findByMember(member);
        return CropResDto.of(cropList);
    }

    public void updateCrop(WaterReqDto waterReqDto) {
        Optional<Crop> crop = cropRepository.findById(waterReqDto.getCropId());
        // List<Code> codeList = codeRepository.findByIdContains("CRT");
        // codeList에서 CRT로 시작하는 code들 들고오기 => sort해서.
        // if lastIndex 이면 잘못된 접근
        // else 문자열 바꿔서 update

        if (waterReqDto.getShorten()) {
            // 성장시간 단축
            String currentState = crop.orElseThrow().getState();
            StringBuilder newState = new StringBuilder(currentState);
            Integer newCode = Integer.parseInt(Character.toString(currentState.charAt(4))) + 1;
            if (newCode != 7) {
                newState.setCharAt(4, newCode.toString().charAt(0));
                crop.orElseThrow().updateCrop(newState.toString());
            }
        } else {
            // 쿨타임 확인
            Integer cooltime = crop.orElseThrow().getCooltime();
            LocalDateTime updDt = crop.orElseThrow().getUpdDt();

            LocalDateTime compare = updDt.plusSeconds(cooltime);
            LocalDateTime now = LocalDateTime.now();

            if (now.isAfter(compare)) {
                String currentState = crop.orElseThrow().getState();
                StringBuilder newState = new StringBuilder(currentState);
                Integer newCode = Integer.parseInt(Character.toString(currentState.charAt(4))) + 1;
                if (newCode != 7) {
                    newState.setCharAt(4, newCode.toString().charAt(0));
                    crop.orElseThrow().updateCrop(newState.toString());
                }
            }
        }


    }

    public MyCode updateSeed(Member member, PlantReqDto plantReqDto) {
        List<Crop> cropList = cropRepository.findByMember(member);
        if (!cropList.isEmpty()) {
            cropList.get(0).updateSeed();
//            MyCode myCode = codeRepository.findById(cropList.get(0).getType()).orElseThrow();
            return null;
        } else {
            return null;
        }
    }

    public String findRandomFarm() {
        // MemberRepository 에서 count 하고, 그 만큼 random 돌리기.
        // @Query(value = "SELECT * FROM hackerton.literature order by RAND() limit 1",nativeQuery = true)
        // List<Literature> findAll();
        // 랜덤 1 row member 추출. address 반환.
        return "1";
    }

    public void registerNFT(Member member, HarvestReqDto harvestReqDto) {
        // NftRepository 에 save
    }


}