package emgc.randomlunch.dto;

import emgc.randomlunch.entity.Question;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionInfoDto {

    private Long id;

    @NotBlank(message = "이메일은 필수값 입니다.")
    @Email(message = "이메일 형식을 준수해야 합니다.")
    private String email;

    @NotBlank(message = "내용은 필수값 입니다.")
    @Size(min = 1, max = 500, message = "내용의 길이는 1~500자 이어야 합니다.")
    private String content;

    public QuestionInfoDto(Question question){
        this.id = question.getId();
        this.email = question.getEmail();
        this.content = question.getContent();
    }
}
