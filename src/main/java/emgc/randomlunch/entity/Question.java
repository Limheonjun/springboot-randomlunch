package emgc.randomlunch.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import emgc.randomlunch.dto.question.QuestionRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

	@Column(length = 30)
	private String title;

	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	public static Question of(QuestionRequest request, User user) {
		String title = request.getTitle();
		String content = request.getContent();

		return Question.builder().title(title).content(content).user(user).build();
	}

}
