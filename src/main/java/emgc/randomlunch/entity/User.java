package emgc.randomlunch.entity;

import emgc.randomlunch.dto.UserDto;
import emgc.randomlunch.enums.Gender;
import emgc.randomlunch.enums.Role;
import emgc.randomlunch.enums.ServiceProvider;
import emgc.randomlunch.security.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User extends BaseTimeEntity implements UserDetails{

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private ServiceProvider serviceProvider;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Thumbnail> thumbnailList = new ArrayList<>();

    private String email;
    private String password;
    private String nickname;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(role.name()));
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
        return this;
    }
}
