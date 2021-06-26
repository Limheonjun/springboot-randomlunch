package emgc.randomlunch.dto;

import emgc.randomlunch.enums.Gender;
import emgc.randomlunch.security.domain.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private long id;
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private Gender gender;
    private String role;

    public UserDto(User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.phoneNumber = user.getPhoneNumber();
        this.gender = user.getGender();
        this.role = user.getUserRole().stream().findFirst().get().getRole().getRoleName();
    }
}
