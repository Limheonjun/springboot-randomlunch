package emgc.randomlunch.api;

import static org.springframework.http.HttpStatus.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import emgc.randomlunch.dto.user.JoinRequest;
import emgc.randomlunch.dto.user.LoginRequest;
import emgc.randomlunch.dto.user.UserResponse;
import emgc.randomlunch.exception.NoSuchUserException;
import emgc.randomlunch.service.function.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserApi {

	private final UserService userService;

	@PostMapping("join")
	public ResponseEntity<Void> join(@RequestBody @Valid JoinRequest request) {
		userService.join(request);
		return new ResponseEntity<>(CREATED);
	}

	@PostMapping("login")
	public ResponseEntity<UserResponse> login(
		@RequestBody @Valid LoginRequest request,
		Authentication authentication,
		HttpServletResponse response
	) {
		UserResponse user = null;

		if (authentication == null) {
			user = userService.login(request);
		} else {
			String email = (String)authentication.getPrincipal();
			user = userService.getUser(email);
		}

		if (user == null) {
			throw new NoSuchUserException();
		}

		String token = com.emgc.payhere.util.JwtUtil.createToken(user.getEmail(), user.getRole());
		response.addHeader("Token", token);
		return new ResponseEntity<>(user, OK);
	}

	@PostMapping("logout")
	public ResponseEntity<Void> logout(HttpServletRequest request) {
		String token = com.emgc.payhere.util.JwtUtil.resolveToken(request);
		userService.logout(token);
		return new ResponseEntity<>(OK);
	}

	@GetMapping("exist/{email}")
	public ResponseEntity<Boolean> isExist(@PathVariable String email) {
		boolean isExist = userService.isExist(email);
		return new ResponseEntity<>(isExist ? CONFLICT : OK);
	}

}
