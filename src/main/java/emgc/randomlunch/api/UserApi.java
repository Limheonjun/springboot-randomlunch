package emgc.randomlunch.api;

import emgc.randomlunch.dto.UserDto;
import emgc.randomlunch.exception.NoSuchUserException;
import emgc.randomlunch.security.domain.Role;
import emgc.randomlunch.security.domain.User;
import emgc.randomlunch.security.domain.UserRole;
import emgc.randomlunch.security.provider.JwtAuthenticationProvider;
import emgc.randomlunch.security.repository.UserRepository;
import emgc.randomlunch.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@Slf4j
@Validated
public class UserApi {

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtAuthenticationProvider jwtAuthenticationProvider;
    @Autowired private UserService userService;

    @PostMapping("/join")
    public void join(
            @RequestBody @Valid UserDto user
    ){
        log.info("Received a request to create a user {} ", user.getEmail());
        userService.joinUser(user);
    }

    @PostMapping("/login")
    public UserDto login(
            @RequestBody @Valid UserDto user,
            HttpServletResponse response
    ) {
        log.info("Received a login request for user {}", user.getEmail());
        User member = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new NoSuchUserException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(user.getPassword(), member.getPassword())) {
            throw new NoSuchUserException("잘못된 비밀번호입니다.");
        }

        String token = jwtAuthenticationProvider.createToken(member.getUsername(),
                member.getUserRole().stream().map(UserRole::getRole).map(Role::getRoleName).collect(Collectors.toList()));

        ResponseCookie cookie = ResponseCookie.from("X-AUTH-TOKEN", token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("None")
                .build();
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return new UserDto(member);
    }

    @PostMapping("/update")
    public UserDto update(
            @RequestBody @Valid UserDto userDto
    ){
        log.info("Received a update request for user {}", userDto.getEmail());
        User user = userRepository.findById(userDto.getId()).orElseThrow(() -> new NoSuchUserException("존재하지 않는 사용자입니다."));
        user.updateInfo(userDto);
        return userDto;
    }

    @PostMapping("/logout")
    public void logout(
            HttpServletResponse response,
            @NotNull(message = "로그인 되어있지 않은 사용자는 로그아웃 할 수 없습니다.") Principal principal
    ){
        log.info("Received a logout request for user {}", principal.getName());
        ResponseCookie cookie = ResponseCookie.from("X-AUTH-TOKEN", null)
                .httpOnly(true)
                .secure(true)
                .maxAge(0)
                .path("/")
                .sameSite("None")
                .build();
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    @GetMapping("/info")
    public UserDto getInfo(
            @NotNull(message = "로그인 되어있지 않은 사용자는 정보를 조회할 수 없습니다.") Principal principal
    ){
        log.info("Received a request to get information about user {}", principal.getName());
        Object details = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(details != null && !(details instanceof  String)) return new UserDto((User) details);
        return null;
    }

}
