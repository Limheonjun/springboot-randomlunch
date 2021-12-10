package emgc.randomlunch.service.function;

import java.util.List;

import emgc.randomlunch.entity.Hashtag;

public interface HashtagService {

	List<Hashtag> createHashtagList(List<String> wordList);

}
