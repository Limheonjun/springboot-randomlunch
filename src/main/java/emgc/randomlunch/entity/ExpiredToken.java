package emgc.randomlunch.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder
public class ExpiredToken extends BaseTimeEntity {

	@Id
	@GeneratedValue
	@Column(name = "expiredtoken_id")
	private Long id;

	private String token;

}
