package onthemars.back.farm.app;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import onthemars.back.nft.entity.NftHistory;
import onthemars.back.nft.entity.Transaction;
import onthemars.back.user.domain.Profile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class HarvestList {

    List<Harvest> harvests;

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    @ToString
    public static class Harvest {

        private Long cropId;

        private String type;

        private String contractAddress;

        private Long tokenId;

        private String dna;

        private String nftImgUrl;


        public Transaction toTransaction(Profile profile) {
            return Transaction.builder()
                .member(profile)
                .contractAddress(this.contractAddress)
                .tokenId(this.tokenId)
                .isBurn(false)
                .dna(this.dna)
                .price(0.0)
                .regDt(LocalDateTime.now())
                .viewCnt(0)
                .isSale(false)
                .imgUrl(this.nftImgUrl)
                .build();
        }

        public NftHistory toNftHistory(Profile profile) {
            return NftHistory.builder()
                .seller(profile)
                .buyer(null)
                .price(0.0)
                .regDt(LocalDateTime.now())
                .eventType("TRC01")
                .build();
        }
    }

}
