package emgc.randomlunch.service.implementation;

import static emgc.randomlunch.enums.Role.*;

import org.springframework.stereotype.Service;

import emgc.randomlunch.dto.JoinRequest;
import emgc.randomlunch.dto.JoinResponse;
import emgc.randomlunch.dto.LoginRequest;
import emgc.randomlunch.dto.LoginResponse;
import emgc.randomlunch.entity.ExpiredToken;
import emgc.randomlunch.entity.User;
import emgc.randomlunch.exception.ExistingUserException;
import emgc.randomlunch.exception.NoSuchUserException;
import emgc.randomlunch.repository.ExpiredTokenRepository;
import emgc.randomlunch.repository.UserRepository;
import emgc.randomlunch.service.function.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

	private final UserRepository userRepository;

	private final ExpiredTokenRepository expiredTokenRepository;

	@Override
	public JoinResponse join(JoinRequest request) {
		if (isExist(request.getEmail())) {
			throw new ExistingUserException();
		}

		User user = User.builder()
			.email(request.getEmail())
			.password(request.getPassword())
			.role(USER)
			.build();

		User savedUser = userRepository.save(user);
		return JoinResponse.from(savedUser);
	}

	@Override
	public LoginResponse login(LoginRequest request) {
		User user = userRepository.findByEmail(request.getEmail()).orElseThrow(NoSuchUserException::new);

		if (!user.getPassword().matches(request.getPassword())) {
			throw new NoSuchUserException();
		}

		return LoginResponse.from(user);
	}

	@Override
	public void logout(String token) {
		ExpiredToken expiredToken = ExpiredToken.builder().token(token).build();
		expiredTokenRepository.save(expiredToken);
	}

	@Override
	public boolean isExist(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public boolean isTokenExpired(String token) {
		return expiredTokenRepository.existsByToken(token);
	}
}
