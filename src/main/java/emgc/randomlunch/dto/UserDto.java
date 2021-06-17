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

    public UserDto(User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getName();
        this.gender = user.getGender();
    }
}
