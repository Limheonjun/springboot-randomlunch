package emgc.randomlunch.controlleradvice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import emgc.randomlunch.dto.ErrorResponse;
import emgc.randomlunch.exception.ExistingUserException;
import emgc.randomlunch.exception.NoSuchUserException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class BusinessControllerAdvice {

	@ExceptionHandler(NoSuchUserException.class)
	protected ResponseEntity<ErrorResponse> handleNoSuchUserException(
		NoSuchUserException e,
		HttpServletRequest request
	) {
		log.error("handleNoSuchUserException", e);
		final ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, request.getRequestURI());
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ExistingUserException.class)
	protected ResponseEntity<ErrorResponse> handleExistingUserException(
		ExistingUserException e,
		HttpServletRequest request
	) {
		log.error("handleExistingUserException", e);
		final ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, request.getRequestURI());
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
	}
}
