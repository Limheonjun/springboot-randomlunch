package emgc.randomlunch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import emgc.randomlunch.entity.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

	List<Restaurant> findAllByNameLike(String name);

}
