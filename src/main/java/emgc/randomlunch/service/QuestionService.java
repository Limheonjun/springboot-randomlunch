package emgc.randomlunch.service;

import emgc.randomlunch.dto.QuestionInfoDto;
import emgc.randomlunch.entity.Question;
import emgc.randomlunch.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public void uploadQuestion(QuestionInfoDto questionInfoDto) {
        Question question = Question.builder()
                .email(questionInfoDto.getEmail())
                .content(questionInfoDto.getContent())
                .build();
        questionRepository.save(question);
    }
}
