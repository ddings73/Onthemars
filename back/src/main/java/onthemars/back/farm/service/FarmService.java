package onthemars.back.farm.service;

import java.time.LocalDateTime;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onthemars.back.aws.AwsS3Utils;
import onthemars.back.common.security.SecurityUtils;
import onthemars.back.exception.UserNotFoundException;
import onthemars.back.farm.domain.Crop;
import onthemars.back.farm.dto.request.StoreReqDto;
import onthemars.back.farm.dto.response.MintResDto;
import onthemars.back.farm.repository.CropRepository;
import onthemars.back.farm.repository.SeedHistoryRepository;
import onthemars.back.user.domain.Member;
import onthemars.back.user.domain.Profile;
import onthemars.back.user.repository.MemberRepository;
import onthemars.back.user.repository.ProfileRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class FarmService {


    //    private final UserService userService;
    private final ProfileRepository profileRepository;

    private final CropRepository cropRepository;

    private final MemberRepository memberRepository;

    private final SeedHistoryRepository seedHistoryRepository;

    private final AwsS3Utils awsS3Utils;


//    private final TransactionRepository transactionRepository;

    public StoreReqDto findFarm(String address) {
        Member member = memberRepository.findById(address).orElseThrow(UserNotFoundException::new);
        Profile profile = profileRepository.findById(address)
            .orElseThrow(UserNotFoundException::new);

        return StoreReqDto.of(profile, cropRepository.findAllByMemberAndPotNumIsNotNull(member));
    }

    public void updateFarm(StoreReqDto storeReqDto) {
//        String currentAddress = SecurityUtils.getCurrentUserId();
        String address = storeReqDto.getPlayer().getAddress();

//        // 권한 없는 사용자가 수정 요청하면 exception 날리기
//        if (!storeReqDto.getPlayer().getAddress().equals(address)) {
//            throw new IllegalSignatureException();
//        }
        log.info(storeReqDto.toString());

        Member member = memberRepository.findById(address).orElseThrow();
        if(storeReqDto.getCropList() != null){
            // crop table update
            storeReqDto.getCropList().getCrops().stream().forEach((cropDto) -> {
                if (cropDto.getCropId() == null && cropDto.getPotNum()!=null) {
                    cropRepository.save(
                        Crop.builder()
                            .type(cropDto.getType())
                            .regDt(LocalDateTime.now())
                            .updDt(LocalDateTime.now())
                            .cooltime(cropDto.getCooltime())
                            .isWatered(cropDto.getIsWaterd())
                            .state(cropDto.getState())
                            .member(member)
                            .build()
                    );
                } else {
                    Crop crop = cropRepository.findById(cropDto.getCropId()).orElseThrow();
                    crop.updateCrop(cropDto);
                }
            });
        }

        // seed history update & profile update
        if (storeReqDto.getPlayer().getBuySeedCnt() != 0) {
            seedHistoryRepository.save(storeReqDto.getPlayer().setSeedHistory(member));
//            profileRepository.findById(address)
//            storeReqDto.getPlayerDto().getBuySeedCnt()
        }

        // 민팅 했다면 tracsaction table insert
        // + nft history table insert

        if (!storeReqDto.getPlayer().getHarvestList().getHaversts().isEmpty()) {
//            transactionRepository.save(
//
//            );
        }


    }

    public String findRandomFarm() {
        String address = SecurityUtils.getCurrentUserId();
        Long profileCnt = profileRepository.count();
        String randAddress = "";
        do {
            int randIdx = (int) (Math.random() * profileCnt);

            Pageable pageable = PageRequest.of(randIdx, 1);
            Page<Profile> randomProfile = profileRepository.findAll(pageable);

            randAddress = randomProfile.getContent().get(0).getAddress();
        } while (address.equals(randAddress));

        return randAddress;
    }


    public MintResDto findImgUrl(){
//        S3Dir.valueOf()
        return new MintResDto();
    }


}