package emgc.randomlunch.service.function;

import emgc.randomlunch.dto.question.QuestionRequest;

public interface QuestionService {

	void uploadQuestion(QuestionRequest request, String email);

}
