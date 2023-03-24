package onthemars.back.aws;

import lombok.Getter;

@Getter
public enum S3Dir {
    PROFILE("images/profile");

    private final String path;

    S3Dir(String path){
        this.path = path;
    }
}
