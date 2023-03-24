package onthemars.back.nft.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class NftActivityListResDto implements Serializable {
//    private final NftActivityItemDto activities;
//
//    @Data
//    private static class NftActivityItemDto {
    private final String event;
    private final String cropParent;
    private final String cropType;
    private final String tokenId;
    //TODO type, bg, eyes, mouth, headGear 추가
    private final Double price;
    private final String from;
    private final String to;
    private final LocalDateTime date;
//    }

}
