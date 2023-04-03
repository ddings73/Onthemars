package onthemars.back.farm.service;

import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onthemars.back.aws.AwsS3Utils;
import onthemars.back.aws.S3Dir;
import onthemars.back.common.security.SecurityUtils;
import onthemars.back.exception.UserNotFoundException;
import onthemars.back.farm.app.CropDto;
import onthemars.back.farm.domain.Crop;
import onthemars.back.farm.dto.request.StoreReqDto;
import onthemars.back.farm.dto.response.LoadResDto;
import onthemars.back.farm.dto.response.MintResDto;
import onthemars.back.farm.repository.CropRepository;
import onthemars.back.farm.repository.SeedHistoryRepository;
import onthemars.back.nft.entity.Transaction;
import onthemars.back.nft.repository.NftHistoryRepository;
import onthemars.back.nft.repository.TransactionRepository;
import onthemars.back.notification.app.NotiTitle;
import onthemars.back.notification.service.NotiService;
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

    private final AwsS3Utils awsS3Utils;

    private final UserService userService;
    private final NotiService notiService;

    private final ProfileRepository profileRepository;

    private final CropRepository cropRepository;

    private final MemberRepository memberRepository;

    private final SeedHistoryRepository seedHistoryRepository;

    private final TransactionRepository transactionRepository;

    private final NftHistoryRepository nftHistoryRepository;

    public LoadResDto findFarm(String address) {
        Member member = memberRepository.findById(address).orElseThrow(UserNotFoundException::new);
        Profile profile = profileRepository.findById(address).orElseThrow();

        List<Crop> cropList = cropRepository.findAllByMemberAndPotNumIsNotNullOrderByPotNum(
            member);

        List<CropDto> collect = cropList.stream().map(CropDto::of)
            .collect(Collectors.toList());
        return LoadResDto.of(profile,
            collect);
    }

    public void updateFarm(StoreReqDto storeReqDto) {
//        String currentAddress = SecurityUtils.getCurrentUserId();
//        log.info(storeReqDto.toString());

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
            storeReqDto.getCropList().stream().forEach((cropDto) -> {
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
//
//         민팅 했다면 tracsaction insert + nft history inser
            storeReqDto.getPlayer().getHarvests().forEach((harvest) -> {
                MultipartFile nftImgFile = harvest.getNftImgFile();
                String nftImgUrl = awsS3Utils.upload(nftImgFile, harvest.getTokenId().toString(), S3Dir.NFT).orElseThrow();

                Transaction transaction = transactionRepository.save(
                    harvest.toTransaction(profile, nftImgUrl)
                );
                nftHistoryRepository.save(
                    harvest.toNftHistory(profile, transaction)
                );
            });



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


    public MintResDto findImgUrl(String dna) {
        // 프론트 에서 받은 dna로 imgUrl 조회
        String cropDna = dna.substring(1, 3);
        String colorDna = dna.substring(3, 5);


        String cropImgUrl = awsS3Utils.S3_PREFIX +awsS3Utils.get(S3Dir.VEGI, cropDna).orElseThrow();
        String colorImgUrl = awsS3Utils.S3_PREFIX + awsS3Utils.get(S3Dir.BG, colorDna).orElseThrow();


        MintResDto mintResDto = MintResDto.builder()
            .colorUrl(colorImgUrl)
            .cropUrl(cropImgUrl)
            .build();

        return mintResDto;
    }


}