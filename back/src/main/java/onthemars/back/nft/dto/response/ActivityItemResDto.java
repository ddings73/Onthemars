package onthemars.back.nft.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import onthemars.back.nft.entity.NftHistory;

@Data
public class ActivityItemResDto implements Serializable {

    public static ActivityItemResDto of(NftHistory nftHistory, String event){
        String fromAddress = "";
        String fromNickname = "";
        String toAddress = "";
        String toNickname = "";

        if (null != nftHistory.getSeller()) {
            fromAddress = nftHistory.getSeller().getAddress();
            fromNickname = nftHistory.getSeller().getNickname();
        }

        if (null != nftHistory.getBuyer()) {
            toAddress = nftHistory.getBuyer().getAddress();
            toNickname = nftHistory.getBuyer().getNickname();
        }

        return ActivityItemResDto.builder()
            .transactionId(nftHistory.getTransaction().getId())
            .event(event)
            .price(nftHistory.getPrice())
            .fromAddress(fromAddress)
            .fromNickname(fromNickname)
            .toAddress(toAddress)
            .toNickname(toNickname)
            .date(nftHistory.getRegDt())
            .build();
    }

    @Builder
    public ActivityItemResDto(
        Long transactionId,
        String event,
        Double price,
        String fromAddress,
        String fromNickname,
        String toAddress,
        String toNickname,
        LocalDateTime date
    ) {
        this.transactionId = transactionId;
        this.event = event;
        this.price = price;
        this.fromAddress = fromAddress;
        this.fromNickname = fromNickname;
        this.toAddress = toAddress;
        this.toNickname = toNickname;
        this.date = date;
    }

    private Long transactionId;
    private String event;
    private Double price;
    private String fromAddress;
    private String fromNickname;
    private String toAddress;
    private String toNickname;
    private LocalDateTime date;

}
