package emgc.randomlunch.dto;

import emgc.randomlunch.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {

	private String email;

	public static LoginResponse from(User user) {
		String email = user.getEmail();
		return LoginResponse.builder().email(email).build();
	}

}
