package emgc.randomlunch.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import emgc.randomlunch.entity.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

	List<Restaurant> findAllByNameLike(String name);

	@Query(value = "SELECT *, "
		+ "(6371*acos(cos(radians(:latitude))*cos(radians(latitude))*cos(radians(longitude)"
		+ "-radians(:longitude))+sin(radians(:latitude))*sin(radians(latitude)))) "
		+ "AS distance "
		+ "FROM restaurant "
		+ "HAVING distance <= :distance  "
		+ "ORDER BY distance "
		+ "LIMIT :offset, :limit", nativeQuery = true)
	List<Restaurant> findAllByGps(
		@Param(value = "latitude") BigDecimal latitude,
		@Param(value = "longitude") BigDecimal longitude,
		@Param(value = "distance") int distance,
		@Param(value = "offset") int offset,
		@Param(value = "limit") int limit
	);

}
