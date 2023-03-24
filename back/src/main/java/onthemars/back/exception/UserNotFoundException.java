package onthemars.back.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(){super("존재하지 않는 회원입니다");}
}
