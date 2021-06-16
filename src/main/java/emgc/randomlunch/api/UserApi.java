package emgc.randomlunch.api;

import emgc.randomlunch.dto.UserDto;
import emgc.randomlunch.enums.CountryCode;
import emgc.randomlunch.security.domain.User;
import emgc.randomlunch.security.provider.JwtAuthenticationProvider;
import emgc.randomlunch.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserApi {

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtAuthenticationProvider jwtAuthenticationProvider;

    @PostMapping("/join")
    public void join(@RequestBody UserDto user){
        userRepository.save(User.builder()
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .gender(user.getGender())
                .roles(Collections.singletonList("ROLE_USER"))
                .build());

    }

    @PostMapping("/login")
    public UserDto login(@RequestBody UserDto user, HttpServletResponse response) {
        User member = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(user.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        String token = jwtAuthenticationProvider.createToken(member.getUsername(), member.getRoles());
        response.setHeader("X-AUTH-TOKEN", token);
        return new UserDto(member);
    }

    @GetMapping("/countrycode")
    public Object getCountryCode(){
        return Arrays.stream(CountryCode.values()).collect(Collectors.toMap(code -> code, code -> code.getCode()));
    }

}
