package onthemars.back.aws;

import lombok.Getter;

@Getter
public enum S3Dir {
    PROFILE("images/profile"),
<<<<<<< HEAD
    NFT("images/nft");
=======
    NFT("images/nft"),
    BG("images/background-color"),
    BANNER("images/banner"),
    EYES("images/eyes"),
    HEADGEAR("images/headgear"),
    MOUTH("images/mouth"),
    VEGI("images/vegi");

>>>>>>> b61b1aa921b64ba8f5da27d6f2b508aef64aeaf2

    private final String path;

    S3Dir(String path) {
        this.path = path;
    }
}
