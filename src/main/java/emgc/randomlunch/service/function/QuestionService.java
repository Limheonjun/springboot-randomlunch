package emgc.randomlunch.service.function;

import emgc.randomlunch.dto.QuestionRequest;
import emgc.randomlunch.entity.Question;

public interface QuestionService {

	void uploadQuestion(QuestionRequest request, Long userId);

}
