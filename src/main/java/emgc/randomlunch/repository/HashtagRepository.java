package emgc.randomlunch.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import emgc.randomlunch.entity.Hashtag;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

	Optional<Hashtag> findByWord(String word);

}
