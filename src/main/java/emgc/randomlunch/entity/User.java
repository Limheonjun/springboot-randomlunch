package emgc.randomlunch.entity;

import static emgc.randomlunch.enums.Role.*;
import static emgc.randomlunch.enums.ServiceProvider.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import emgc.randomlunch.dto.user.JoinRequest;
import emgc.randomlunch.enums.Role;
import emgc.randomlunch.enums.ServiceProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User extends BaseTimeEntity implements UserDetails {

	@Id
	@GeneratedValue
	@Column(name = "user_id")
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ServiceProvider serviceProvider;

	@Enumerated(EnumType.STRING)
	private Role role;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Thumbnail> thumbnailList = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	private List<Question> questionList = new ArrayList<>();

	@Column(unique = true, nullable = false, updatable = false)
	private String email;

	private String password;

	@Column(nullable = false, length = 20)
	private String nickname;

	public static User from(JoinRequest request) {
		String email = request.getEmail();
		String password = request.getPassword();
		String nickname = request.getNickname();

		return User.builder()
			.email(request.getEmail())
			.password(request.getPassword())
			.nickname(nickname)
			.serviceProvider(LOCAL)
			.role(USER)
			.build();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority(getRole().name()));
	}

	@Override
	public String getUsername() {
		return getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}
}
