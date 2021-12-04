package emgc.randomlunch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import emgc.randomlunch.entity.Hashtag;
import emgc.randomlunch.entity.ThumbnailHashtag;

public interface ThumbnailHashtagRepository extends JpaRepository<ThumbnailHashtag, Long> {

	List<ThumbnailHashtag> findAllByHashtagIn(List<Hashtag> hashtagList);

}
