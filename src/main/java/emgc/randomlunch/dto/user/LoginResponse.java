package emgc.randomlunch.dto.user;

import emgc.randomlunch.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {

	private Long userId;

	private String email;

	private String role;

	public static LoginResponse from(User user) {
		Long userId = user.getId();
		String email = user.getEmail();
		String role = user.getRole().name();

		return LoginResponse.builder().userId(userId).email(email).role(role).build();
	}

}
