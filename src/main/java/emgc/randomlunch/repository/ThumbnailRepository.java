package emgc.randomlunch.repository;

import emgc.randomlunch.entity.Restaurant;
import emgc.randomlunch.entity.Thumbnail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThumbnailRepository extends JpaRepository<Thumbnail, Long> {

    List<Thumbnail> findFirst8ByRestaurant(Restaurant restaurant);

}
