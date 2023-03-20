package onthemars.back.farm.service;

import lombok.RequiredArgsConstructor;
import onthemars.back.farm.dto.request.RegisterSeedHistoryReqDto;
import onthemars.back.farm.dto.response.CropListResDto;
import onthemars.back.farm.dto.response.SeedCntResDto;
import onthemars.back.farm.repository.CropRepository;
import onthemars.back.farm.repository.SeedHistoryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FarmService {

    private final CropRepository farmRepository;
    private final SeedHistoryRepository seedHistoryRepository;

    public void registerSeedHistory(RegisterSeedHistoryReqDto registerSeedHistoryReqDto) {
        seedHistoryRepository.save(registerSeedHistoryReqDto);
    }

    public SeedCntResDto countSeed() {
        // usertable에서 조회해야함
        SeedCntResDto seedCntResDto = null;
        seedCntResDto.setSeedCnt(2);
        return seedCntResDto;
    }

    public CropListResDto findAllCrop() {
        CropListResDto cropListResDto = null;
//        cropListResDto.setCropDto("1","1","2023-03-21", "2023-03-21", 10, 1, 1, "SEED");
        return cropListResDto;
    }

    public void updateCrop(){

    }




}