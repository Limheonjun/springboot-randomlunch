package emgc.randomlunch.service;

import emgc.randomlunch.security.domain.User;
import emgc.randomlunch.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired private UserRepository repository;

    public User getUser(User user) {
        return getUser(user.getId());
    }

    public User getUser(Long id) {
        return repository.findById(id).orElseThrow();
    }


}
