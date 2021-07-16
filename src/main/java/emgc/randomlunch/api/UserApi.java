package emgc.randomlunch.api;

import emgc.randomlunch.dto.UserDto;
import emgc.randomlunch.security.domain.Role;
import emgc.randomlunch.security.domain.User;
import emgc.randomlunch.security.domain.UserRole;
import emgc.randomlunch.security.provider.JwtAuthenticationProvider;
import emgc.randomlunch.security.repository.RoleRepository;
import emgc.randomlunch.security.repository.UserRepository;
import emgc.randomlunch.security.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserApi {

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtAuthenticationProvider jwtAuthenticationProvider;
    @Autowired private RoleRepository roleRepository;
    @Autowired private UserRoleRepository userRoleRepository;

    @PostMapping("/join")
    public void join(@RequestBody UserDto user){
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

    @PostMapping("/login")
    public UserDto login(@RequestBody UserDto user, HttpServletResponse response) {
        User member = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(user.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        String token = jwtAuthenticationProvider.createToken(member.getUsername(),
                member.getUserRole().stream().map(UserRole::getRole).map(Role::getRoleName).collect(Collectors.toList()));

        Cookie cookie = new Cookie("X-AUTH-TOKEN", token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);

        return new UserDto(member);
    }

    @PostMapping("/update")
    public UserDto update(@RequestBody UserDto userDto){
        User user = userRepository.findById(userDto.getId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        user.updateInfo(userDto);
        return userDto;
    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse response){
        Cookie cookie = new Cookie("X-AUTH-TOKEN", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @GetMapping("/info")
    public UserDto getInfo(){
        Object details = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(details != null && !(details instanceof  String)) return new UserDto((User) details);
        return null;
    }

}
