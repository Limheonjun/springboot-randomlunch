package emgc.randomlunch.api;

import emgc.randomlunch.entity.Question;
import emgc.randomlunch.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionApi {

    private final QuestionRepository questionRepository;

    @PostMapping("/upload")
    public void uploadQuestion(@RequestBody  Question question) {
        questionRepository.save(question);
    }
}
