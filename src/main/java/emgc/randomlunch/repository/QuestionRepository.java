package emgc.randomlunch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import emgc.randomlunch.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
