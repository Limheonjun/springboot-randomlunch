package emgc.randomlunch.controlleradvice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import emgc.randomlunch.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e,
        HttpServletRequest request
    ) {
        log.error("handleMethodArgumentNotValidException", e);
        final ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, request.getRequestURI(), e.getBindingResult());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
        HttpRequestMethodNotSupportedException e,
        HttpServletRequest request
    ) {
        log.error("handleHttpRequestMethodNotSupportedException", e);
        final ErrorResponse response = ErrorResponse.of(HttpStatus.METHOD_NOT_ALLOWED, request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(
        Exception e,
        HttpServletRequest request
    ) {
        log.error("handleEntityNotFoundException", e);
        final ErrorResponse response = ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, request.getRequestURI()   );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
