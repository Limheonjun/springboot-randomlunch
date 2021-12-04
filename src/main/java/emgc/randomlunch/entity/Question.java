package emgc.randomlunch.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "question_id")
	private Long id;
	private String content;

}
