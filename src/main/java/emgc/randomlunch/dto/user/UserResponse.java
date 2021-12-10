package emgc.randomlunch.dto.user;

import emgc.randomlunch.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {

	private Long userId;

	private String email;

	private String role;

	public static UserResponse from(User user) {
		Long userId = user.getId();
		String email = user.getEmail();
		String role = user.getRole().name();

		return UserResponse.builder().userId(userId).email(email).role(role).build();
	}

}
