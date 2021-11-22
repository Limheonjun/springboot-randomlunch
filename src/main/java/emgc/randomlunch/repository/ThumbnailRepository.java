package emgc.randomlunch.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import emgc.randomlunch.entity.Restaurant;
import emgc.randomlunch.entity.Thumbnail;
import emgc.randomlunch.security.domain.User;

public interface ThumbnailRepository extends JpaRepository<Thumbnail, Long> {

	List<Thumbnail> findFirst8ByRestaurant(Restaurant restaurant);

	List<Thumbnail> findByRestaurant(Restaurant restaurant, Pageable pageable);

	List<Thumbnail> findByUser(User user, Pageable pageable);

}
