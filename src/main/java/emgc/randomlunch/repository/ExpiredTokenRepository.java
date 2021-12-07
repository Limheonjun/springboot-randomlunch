package emgc.randomlunch.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import emgc.randomlunch.entity.ExpiredToken;

public interface ExpiredTokenRepository extends JpaRepository<ExpiredToken, Long> {

	boolean existsByToken(String token);

	Optional<ExpiredToken> findByToken(String token);

}
