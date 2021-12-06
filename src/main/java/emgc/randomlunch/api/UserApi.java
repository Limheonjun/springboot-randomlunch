package emgc.randomlunch.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import emgc.randomlunch.service.function.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserApi {

	private final UserService userService;

}
