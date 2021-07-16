package emgc.randomlunch.dto;

import emgc.randomlunch.entity.Question;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionInfoDto {

    private Long id;
    private String email;
    private String content;

    public QuestionInfoDto(Question question){
        this.id = question.getId();
        this.email = question.getEmail();
        this.content = question.getContent();
    }
}
