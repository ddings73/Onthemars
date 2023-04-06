package onthemars.back.farm.app;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import onthemars.back.nft.entity.NftHistory;
import onthemars.back.nft.entity.Transaction;
import onthemars.back.user.domain.Profile;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Harvest {

    private Long cropId;

    private String type;

    private String contractAddress;

    private Long tokenId;

    private String dna;

    private MultipartFile nftImgFile;


    public Transaction toTransaction(Profile profile, String nftImgUrl) {
        return Transaction.builder()
            .member(profile)
            .contractAddress(this.contractAddress)
            .tokenId(this.tokenId)
            .isBurn(false)
            .dna(this.dna)
            .price(-1.0)
            .regDt(LocalDateTime.now())
            .viewCnt(0)
            .isSale(false)
            .imgUrl(nftImgUrl)
            .updDt(LocalDateTime.now())
            .build();
    }

    public NftHistory toNftHistory(Profile profile, Transaction transaction) {
        return NftHistory.builder()
            .seller(null)
            .buyer(profile)
            .transaction(transaction)
            .price(-1.0)
            .regDt(LocalDateTime.now())
            .eventType("TRC01")
            .build();

    }

}
