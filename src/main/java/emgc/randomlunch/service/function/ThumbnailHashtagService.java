package emgc.randomlunch.service.function;

import java.util.List;

import emgc.randomlunch.entity.Hashtag;
import emgc.randomlunch.entity.ThumbnailHashtag;

public interface ThumbnailHashtagService {

	List<ThumbnailHashtag> getThumbnailHashtagListByHashtagList(List<Hashtag> hashtagList);

}
