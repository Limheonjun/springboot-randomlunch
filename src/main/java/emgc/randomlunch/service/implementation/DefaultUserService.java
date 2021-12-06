package emgc.randomlunch.service.implementation;

import org.springframework.stereotype.Service;

import emgc.randomlunch.entity.User;
import emgc.randomlunch.service.function.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

	@Override
	public User join(User user) {
		return null;
	}

	@Override
	public User login(User user) {
		return null;
	}

	@Override
	public void logout() {

	}

}
