package onthemars.back.farm.service;

import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onthemars.back.aws.AwsS3Utils;
import onthemars.back.aws.S3Dir;
import onthemars.back.common.security.SecurityUtils;
import onthemars.back.exception.UserNotFoundException;
import onthemars.back.farm.domain.Crop;
import onthemars.back.farm.dto.response.LoadResDto;
import onthemars.back.farm.dto.response.MintResDto;
import onthemars.back.farm.dto.response.StoreReqDto;
import onthemars.back.farm.repository.CropRepository;
import onthemars.back.farm.repository.SeedHistoryRepository;
import onthemars.back.nft.repository.NftHistoryRepository;
import onthemars.back.nft.repository.TransactionRepository;
import onthemars.back.user.domain.Member;
import onthemars.back.user.domain.Profile;
import onthemars.back.user.repository.MemberRepository;
import onthemars.back.user.repository.ProfileRepository;
import onthemars.back.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class FarmService {


    private final UserService userService;
    private final ProfileRepository profileRepository;

    private final CropRepository cropRepository;

    private final MemberRepository memberRepository;

    private final SeedHistoryRepository seedHistoryRepository;

    private final AwsS3Utils awsS3Utils;

    private final TransactionRepository transactionRepository;

    private final NftHistoryRepository nftHistoryRepository;

    public LoadResDto findFarm(String address) {
        Member member = memberRepository.findById(address).orElseThrow(UserNotFoundException::new);
        Profile profile = profileRepository.findById(address).orElseThrow();

        return LoadResDto.of(profile,
            cropRepository.findAllByMemberAndPotNumIsNotNullOrderByPotNum(member));
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
        Profile profile = profileRepository.findById(address).orElseThrow();
        if (storeReqDto.getCropList() != null) {
            // 모든 사용자 화분 초기화
            // 게임 데이터가 중복으로 들어가는 것을 방지
            cropRepository.findAllByMember(member).stream().forEach((crop) -> {
                crop.setPotNumNull();
            });

            // crop update
            storeReqDto.getCropList().getCrops().stream().forEach((cropDto) -> {
                if (cropDto.getCropId() == null && cropDto.getPotNum() != null) {
                    // 게임에서 새로 작물을 화분에 심은 상태
                    cropRepository.save(
                        cropDto.toCrop(member)
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
            userService.findProfile(address).updateSeedCnt(storeReqDto.getPlayer().getBuySeedCnt());
        }

        int fileIndex = 0;
//         민팅 했다면 tracsaction insert + nft history insert
        if (!storeReqDto.getPlayer().getHarvestList().getHarvests().isEmpty()) {
            storeReqDto.getPlayer().getHarvestList().getHarvests().forEach((harvest) -> {
                MultipartFile nftImgFile = storeReqDto.getNftImgFile().get(fileIndex);
                String nftImgUrl = awsS3Utils.upload(nftImgFile,
                        storeReqDto.getPlayer().getHarvestList().getHarvests().get(fileIndex)
                            .getTokenId().toString(), S3Dir.NFT)
                    .orElseThrow();
                harvest.setNftImgUrl(nftImgUrl);
                transactionRepository.save(
                    harvest.toTransaction(profile)
                );
                nftHistoryRepository.save(
                    harvest.toNftHistory(profile)
                );
            });
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


    public MintResDto findImgUrl(String cropType) {

        String cropImgUrl =
            "/" + S3Dir.VEGI.getPath() + "/" + cropType.substring(3) + ".png";
        Integer num = (int) (Math.random() * 10) + 1;
        String colorCode = "";

        if (num < 10) {
            colorCode += "0" + num;
        } else {
            colorCode += num;
        }

        String colorImgUrl = "/" + S3Dir.BG.getPath() + "/" + colorCode + ".png";

        MintResDto mintResDto = MintResDto.builder()
            .colorUrl(colorImgUrl)
            .color("CLR" + colorCode)
            .cropUrl(cropImgUrl)
            .build();

        return mintResDto;
    }


}