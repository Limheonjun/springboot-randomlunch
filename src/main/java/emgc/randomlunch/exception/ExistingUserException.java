package emgc.randomlunch.exception;

public class ExistingUserException extends RuntimeException{

	private static final String MESSAGE = "이미 존재하는 사용자입니다.";

	public ExistingUserException() {
		super(MESSAGE);
	}
}
