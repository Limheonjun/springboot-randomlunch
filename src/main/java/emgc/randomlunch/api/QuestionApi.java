package emgc.randomlunch.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import emgc.randomlunch.service.implementation.DefaultQuestionService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("question")
@RequiredArgsConstructor
public class QuestionApi {

	private final DefaultQuestionService questionService;

}
