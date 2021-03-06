package emgc.randomlunch.repository;

import emgc.randomlunch.entity.Category;
import emgc.randomlunch.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findByCategory(Category category);
}
