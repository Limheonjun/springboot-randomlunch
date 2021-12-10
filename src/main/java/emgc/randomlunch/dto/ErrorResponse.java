package emgc.randomlunch.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorResponse {

	private LocalDateTime timestamp;

	private int status;

	private String path;

	private List<Error> errors;

	public static ErrorResponse of(HttpStatus errorCode, String path, BindingResult bindingResult) {

		return ErrorResponse.builder()
			.status(errorCode.value())
			.errors(processError(bindingResult))
			.timestamp(LocalDateTime.now())
			.path(path)
			.build();
	}

	private static List<Error> processError(BindingResult bindingResult) {
		List<Error> errors = new ArrayList<>();

		for(FieldError fieldError : bindingResult.getFieldErrors()) {
			String field = fieldError.getField();
			Object value = fieldError.getRejectedValue();
			String message = fieldError.getDefaultMessage();
			errors.add(Error.builder().field(field).value(value).message(message).build());
		}

		return errors;
	}

	@Getter
	@Builder
	public static class Error {
		private String field;
		private Object value;
		private String message;
	}

}
