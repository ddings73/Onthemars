package onthemars.back.exception.handler;

import lombok.extern.slf4j.Slf4j;
import onthemars.back.exception.IllegalSignatureException;
import onthemars.back.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity handleUserNotFoundException(UserNotFoundException ex){
        log.warn("UserNotFoundException => {}", ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity handleIllegalArgumentException(IllegalArgumentException ex){
        log.warn("IllegalArgumentException => {}", ex.getMessage());
        return ResponseEntity.badRequest().body("잘못된 요청 데이터입니다");
    }

    @ExceptionHandler(IllegalSignatureException.class)
    public ResponseEntity handleIllegalSignatureException(IllegalSignatureException ex){
        log.warn("IllegalSignatureException => {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
}
