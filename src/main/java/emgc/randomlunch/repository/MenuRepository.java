package emgc.randomlunch.repository;

import emgc.randomlunch.entity.Menu;
import emgc.randomlunch.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findByRestaurantOrderByPriceAsc(Restaurant restaurant);

}
