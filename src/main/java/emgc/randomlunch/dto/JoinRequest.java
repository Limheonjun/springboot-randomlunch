package emgc.randomlunch.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JoinRequest {

	private String email;

	private String password;

}
