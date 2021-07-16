package emgc.randomlunch.api;

import emgc.randomlunch.dto.QuestionInfoDto;
import emgc.randomlunch.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionApi {

    private final QuestionService questionService;

    @PostMapping("/upload")
    public void uploadQuestion(@RequestBody QuestionInfoDto questionInfoDto) {
        questionService.uploadQuestion(questionInfoDto);
    }
}
