package emgc.randomlunch.security.domain;

import emgc.randomlunch.dto.UserDto;
import emgc.randomlunch.entity.Thumbnail;
import emgc.randomlunch.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;

    private String email;
    private String password;
    private String phoneNumber;
    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Thumbnail> thumbnailList = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User updateInfo(UserDto userDto){
        this.email = userDto.getEmail();
        this.password = userDto.getPassword();
        this.phoneNumber = userDto.getPhoneNumber();
        this.name = userDto.getName();
        this.gender = userDto.getGender();
        return this;
    }
}
