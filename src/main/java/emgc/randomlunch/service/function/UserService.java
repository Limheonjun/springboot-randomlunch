package emgc.randomlunch.service.function;

import emgc.randomlunch.entity.User;

public interface UserService {

	User join(User user);

	User login(User user);

	void logout();

}
