package emgc.randomlunch.dto.user;

import emgc.randomlunch.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JoinResponse {

	private String email;

	private String role;

	public static JoinResponse from(User user) {
		String email = user.getEmail();
		String role = user.getRole().name();

		return JoinResponse.builder().email(email).role(role).build();
	}

}
