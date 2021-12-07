package emgc.randomlunch.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
public class User extends BaseTimeEntity {

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

	@OneToMany(mappedBy = "user")
	private List<Question> questionList = new ArrayList<>();

	private String email;

	private String password;

	private String nickname;

}
