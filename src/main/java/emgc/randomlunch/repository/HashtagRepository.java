package emgc.randomlunch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import emgc.randomlunch.entity.Hashtag;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

	List<Hashtag> findAllByWordLike(String word);

	List<Hashtag> findAllByWordIn(List<String> wordList);

}
