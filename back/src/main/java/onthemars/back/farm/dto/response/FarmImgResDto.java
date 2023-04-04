package onthemars.back.farm.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import onthemars.back.nft.entity.Transaction;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@ToString
public class FarmImgResDto {
    private List<String> farmImgList;

    public List<String> of(List<Transaction> transactionList){
        transactionList.stream().forEach((transaction)->{
            farmImgList.add(transaction.getImgUrl());
        });
        return this.farmImgList;
    }
}
