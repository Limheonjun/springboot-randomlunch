package emgc.randomlunch.api;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import emgc.randomlunch.dto.question.QuestionRequest;
import emgc.randomlunch.exception.NoSuchUserException;
import emgc.randomlunch.service.implementation.DefaultQuestionService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("questions")
@RequiredArgsConstructor
public class QuestionApi {

	private final DefaultQuestionService questionService;

	@PostMapping("")
	public void uploadQuestion(@RequestBody @Valid QuestionRequest request, Principal principal) {
		if(principal == null) {
			throw new NoSuchUserException();
		}

		questionService.uploadQuestion(request, principal.getName());
	}

}
