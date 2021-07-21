package emgc.randomlunch.api;

import emgc.randomlunch.dto.QuestionInfoDto;
import emgc.randomlunch.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
@Slf4j
public class QuestionApi {

    private final QuestionService questionService;

    @PostMapping("/upload")
    public void uploadQuestion(@RequestBody QuestionInfoDto questionInfoDto, Principal principal) {
        log.info("Received a request to create a question by {} ", principal.getName());
        questionService.uploadQuestion(questionInfoDto);
    }
}
