package onthemars.back.nft.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import onthemars.back.nft.entity.NftHistory;

@Data
public class UserActivityItemResDto implements Serializable {

    public static UserActivityItemResDto of(
        NftHistory nftHistory,
        String event,
        String cropParent,
        String nftName
    ){
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

        return UserActivityItemResDto.builder()
            .imgUrl(nftHistory.getNft().getImgUrl())
            .cropParent(cropParent)
            .nftName(nftName)
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
    public UserActivityItemResDto(
        String imgUrl,
        String cropParent,
        String nftName,
        String event,
        Double price,
        String fromAddress,
        String fromNickname,
        String toAddress,
        String toNickname,
        LocalDateTime date
    ) {
        this.imgUrl = imgUrl;
        this.cropParent = cropParent;
        this.nftName = nftName;
        this.event = event;
        this.price = price;
        this.fromAddress = fromAddress;
        this.fromNickname = fromNickname;
        this.toAddress = toAddress;
        this.toNickname = toNickname;
        this.date = date;
    }

    private String imgUrl;
    private String cropParent;
    private String nftName;
    private String event;
    private Double price;
    private String fromAddress;
    private String fromNickname;
    private String toAddress;
    private String toNickname;
    private LocalDateTime date;

}
