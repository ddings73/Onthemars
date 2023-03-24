package onthemars.back.exception;

public class IllegalSignatureException extends RuntimeException{
    public IllegalSignatureException(){
        super("잘못된 Signature 요청입니다");
    }
}
