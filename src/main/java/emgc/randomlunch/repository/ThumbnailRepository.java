package emgc.randomlunch.repository;

import emgc.randomlunch.entity.Restaurant;
import emgc.randomlunch.entity.Thumbnail;
import emgc.randomlunch.security.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThumbnailRepository extends JpaRepository<Thumbnail, Long> {

    List<Thumbnail> findFirst8ByRestaurant(Restaurant restaurant);
    List<Thumbnail> findByRestaurant(Restaurant restaurant, Pageable pageable);
    List<Thumbnail> findByUser(User user, Pageable pageable);
}
