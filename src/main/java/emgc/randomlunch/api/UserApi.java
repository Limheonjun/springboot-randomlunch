package emgc.randomlunch.api;

import static org.springframework.http.HttpStatus.*;

import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import emgc.randomlunch.dto.JoinRequest;
import emgc.randomlunch.dto.LoginRequest;
import emgc.randomlunch.dto.LoginResponse;
import emgc.randomlunch.service.function.UserService;
import emgc.randomlunch.util.JwtUtil;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserApi {

	private final UserService userService;

	private final JwtUtil jwtUtil;

	@PostMapping("join")
	public ResponseEntity<Void> join(@RequestBody JoinRequest request) {
		userService.join(request);
		return new ResponseEntity<>(CREATED);
	}

	@PostMapping("login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, HttpServletResponse response) {
		LoginResponse loginResponse = userService.login(request);
		String token = jwtUtil.createToken(loginResponse.getEmail(), Arrays.asList(loginResponse.getRole()));
		response.addHeader("Token", token);
		return new ResponseEntity<>(loginResponse, OK);
	}

	@PostMapping("logout")
	public void logout() {

	}

	@GetMapping("exist/{email}")
	public ResponseEntity<Boolean> isExist(@PathVariable String email) {
		boolean isExist = userService.isExist(email);
		return new ResponseEntity<>(isExist ? CONFLICT : OK);
	}

}
