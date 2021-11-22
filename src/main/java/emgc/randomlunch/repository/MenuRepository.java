package emgc.randomlunch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import emgc.randomlunch.entity.Menu;
import emgc.randomlunch.entity.Restaurant;

public interface MenuRepository extends JpaRepository<Menu, Long> {

	List<Menu> findByRestaurantOrderByPriceAsc(Restaurant restaurant);

}
