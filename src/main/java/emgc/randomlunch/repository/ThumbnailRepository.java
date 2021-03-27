package emgc.randomlunch.repository;

import emgc.randomlunch.entity.Restaurant;
import emgc.randomlunch.entity.Thumbnail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ThumbnailRepository extends JpaRepository<Thumbnail, Long> {

    List<Thumbnail> findFirst8ByRestaurant(Restaurant restaurant);
    List<Thumbnail> findByRestaurant(Restaurant restaurant, Pageable pageable);


}
