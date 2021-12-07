package emgc.randomlunch.service.implementation;

import org.springframework.stereotype.Service;

import emgc.randomlunch.dto.QuestionRequest;
import emgc.randomlunch.entity.Question;
import emgc.randomlunch.entity.User;
import emgc.randomlunch.repository.QuestionRepository;
import emgc.randomlunch.repository.UserRepository;
import emgc.randomlunch.service.function.QuestionService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefaultQuestionService implements QuestionService {

	private final QuestionRepository questionRepository;

	private final UserRepository userRepository;

	@Override
	public void uploadQuestion(QuestionRequest request, Long userId) {
		User user = userRepository.findById(userId).orElseThrow();//TODO :구체적인 예외 처리
		Question question = Question.of(request, user);
		questionRepository.save(question);
	}
}
