package emgc.randomlunch.service.implementation;

import org.springframework.stereotype.Service;

import emgc.randomlunch.entity.Question;
import emgc.randomlunch.repository.QuestionRepository;
import emgc.randomlunch.service.function.QuestionService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefaultQuestionService implements QuestionService {

	private final QuestionRepository questionRepository;

	@Override
	public void uploadQuestion(Question question) {
		questionRepository.save(question);
	}
}
