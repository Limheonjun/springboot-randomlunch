package emgc.randomlunch.service;

import emgc.randomlunch.dto.UserDto;
import emgc.randomlunch.security.domain.Role;
import emgc.randomlunch.security.domain.User;
import emgc.randomlunch.security.domain.UserRole;
import emgc.randomlunch.security.repository.RoleRepository;
import emgc.randomlunch.security.repository.UserRepository;
import emgc.randomlunch.security.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public User getUser(User user) {
        return getUser(user.getId());
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void joinUser(UserDto user){
        User joinUser = User.builder()
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .gender(user.getGender())
                .build();
        userRepository.save(joinUser);

        Role role =  Role.builder()
                .roleName("ROLE_USER")
                .roleDesc("일반사용자")
                .build();
        roleRepository.save(role);

        UserRole userRole = UserRole.builder()
                .role(role)
                .user(joinUser)
                .build();
        userRoleRepository.save(userRole);
    }


}
