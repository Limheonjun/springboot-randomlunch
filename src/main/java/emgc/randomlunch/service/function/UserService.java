package emgc.randomlunch.service.function;

import emgc.randomlunch.dto.user.JoinRequest;
import emgc.randomlunch.dto.user.LoginRequest;
import emgc.randomlunch.dto.user.UserResponse;

public interface UserService {

	void join(JoinRequest request);

	UserResponse login(LoginRequest request);

	void logout(String token);

	UserResponse getUser(String email);

	boolean isExist(String email);

	boolean isTokenExpired(String token);

}
