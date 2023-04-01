package onthemars.back.farm.app;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class HarvestList {
    List<Haverst> haversts;
    @Getter
    @AllArgsConstructor
    @Builder
    @ToString
    private static class Haverst{

        private Long cropId;

        private String type;

        private String contractAddress;

        private Long tokenId;

        private String dna;

        private MultipartFile nftImgFile;

//        public Transaction toTransaction(){
//            return Transaction.builder()
//                .
//                .regDt(LocalDateTime.now())
//                .price(0)
//                .activated(false)
//
//                .build();
//        }
    }

}
