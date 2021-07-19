package emgc.randomlunch.api;

import emgc.randomlunch.dto.UserDto;
import emgc.randomlunch.security.domain.Role;
import emgc.randomlunch.security.domain.User;
import emgc.randomlunch.security.domain.UserRole;
import emgc.randomlunch.security.provider.JwtAuthenticationProvider;
import emgc.randomlunch.security.repository.RoleRepository;
import emgc.randomlunch.security.repository.UserRepository;
import emgc.randomlunch.security.repository.UserRoleRepository;
import emgc.randomlunch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
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
    @Autowired private UserService userService;
    @Autowired private RoleRepository roleRepository;
    @Autowired private UserRoleRepository userRoleRepository;

    @PostMapping("/join")
    public void join(@RequestBody UserDto user){
        userService.joinUser(user);
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


//        Cookie cookie = new Cookie("X-AUTH-TOKEN", token);
//        cookie.setPath("/");
//        cookie.setHttpOnly(true);
//        cookie.setSecure(true);
//        response.addCookie(cookie);
        ResponseCookie cookie = ResponseCookie.from("X-AUTH-TOKEN", token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("None")
                .domain("randomlunch.ga")
                .build();
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

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
