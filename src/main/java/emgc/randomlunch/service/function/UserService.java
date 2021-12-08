package emgc.randomlunch.service.function;

import emgc.randomlunch.dto.JoinRequest;
import emgc.randomlunch.dto.JoinResponse;
import emgc.randomlunch.dto.LoginRequest;
import emgc.randomlunch.dto.LoginResponse;

public interface UserService {

	JoinResponse join(JoinRequest request);

	LoginResponse login(LoginRequest request);

	void logout(String token);

	boolean isExist(String email);

	boolean isTokenExpired(String token);

}
