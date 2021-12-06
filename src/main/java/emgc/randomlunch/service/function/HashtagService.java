package emgc.randomlunch.service.function;

import java.util.List;

import emgc.randomlunch.entity.Hashtag;

public interface HashtagService {

	List<Hashtag> getHashtagListByWord(String word);

}
