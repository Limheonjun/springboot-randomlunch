package emgc.randomlunch.dto;

import emgc.randomlunch.enums.Gender;
import emgc.randomlunch.security.domain.User;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    @NotBlank(message = "이메일은 필수값 입니다.")
    @Email(message = "이메일 형식을 준수해야 합니다.")
    private String email;

    private String password;

    @NotBlank(message = "이름은 필수값 입니다.")
    private String name;

    private String phoneNumber;
    private Gender gender;
    private String role;

    public UserDto(User user){
        this.id = user.getId() != null ? user.getId() : null;
        this.email = user.getEmail();
        this.name = user.getName();
        this.phoneNumber = user.getPhoneNumber();
        this.gender = user.getGender();
        this.role = user.getUserRole().stream().findFirst().get().getRole().getRoleName();
    }
}
