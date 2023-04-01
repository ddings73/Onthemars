package onthemars.back.aws;

import lombok.Getter;

@Getter
public enum S3Dir {
    PROFILE("images/profile"),
    NFT("images/nft"),
    BG("images/background-color"),
    BANNER("images/banner"),
    EYES("images/eyes"),
    HEADGEAR("images/headgear"),
    MOUTH("images/mouth"),
    VEGI("images/vegi");


    private final String path;

    S3Dir(String path) {
        this.path = path;
    }
}
