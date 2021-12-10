package emgc.randomlunch.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JoinRequest {

	private String email;

	private String password;

}
