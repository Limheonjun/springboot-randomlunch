package emgc.randomlunch.api;

import emgc.randomlunch.security.domain.User;
import emgc.randomlunch.security.provider.JwtAuthenticationProvider;
import emgc.randomlunch.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

@RestController("/user")
public class UserApi {

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtAuthenticationProvider jwtAuthenticationProvider;

    @PostMapping("/join")
    public void join(@RequestBody Map<String, String> user){
        userRepository.save(User.builder()
                .email(user.get("email"))
                .password(passwordEncoder.encode(user.get("password")))
                .roles(Collections.singletonList("ROLE_USER"))
                .build());

    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> user, HttpServletResponse response) {
        User member = userRepository.findByEmail(user.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        String token = jwtAuthenticationProvider.createToken(member.getUsername(), member.getRoles());
        response.setHeader("X-AUTH-TOKEN", token);
        return token;
    }
}
