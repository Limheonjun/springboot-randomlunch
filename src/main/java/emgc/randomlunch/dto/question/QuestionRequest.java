package emgc.randomlunch.dto.question;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequest {

	private String title;

	@NotBlank(message = "내용은 필수값 입니다.")
	@Size(min = 1, max = 500, message = "내용의 길이는 1~500자 이어야 합니다.")
	private String content;

}
