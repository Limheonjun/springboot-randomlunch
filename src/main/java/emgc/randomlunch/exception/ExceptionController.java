package emgc.randomlunch.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity methodValidationException(
            MethodArgumentNotValidException e,
            HttpServletRequest request,
            Principal principal
    ){
        String user = principal == null ? "anonymous" : principal.getName();
        String message = e.getAllErrors().get(0).getDefaultMessage();
        log.info("MethodArgumentNotValidException occured | user : {} , url : {} , trace : {}", user, request.getRequestURI(), e.getStackTrace());
        log.info("Error Message : {}", message);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchUserException.class)
    public ResponseEntity methodValidationException(
            NoSuchUserException e,
            HttpServletRequest request
    ){
        String message = e.getMessage();
        log.info("NoSuchUserException occured | url : {} , trace : {}", request.getRequestURI(), e.getStackTrace());
        log.info("Error Message : {}", message);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity illegalArgumentException(
            IllegalArgumentException e,
            HttpServletRequest request
    ){
        String message = e.getMessage();
        log.info("IllegalArgumentException occured | url : {} , trace : {}", request.getRequestURI(), e.getStackTrace());
        log.info("Error Message : {}", message);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
