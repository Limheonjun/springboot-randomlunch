package emgc.randomlunch.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import emgc.randomlunch.entity.Restaurant;
import emgc.randomlunch.enums.RestaurantStatus;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

	List<Restaurant> findAllByNameLike(String name);

	@Query(value = "SELECT *, "
		+ "(6371*acos(cos(radians(:latitude))*cos(radians(latitude))*cos(radians(longitude)"
		+ "-radians(:longitude))+sin(radians(:latitude))*sin(radians(latitude)))) "
		+ "AS distance "
		+ "FROM restaurant "
		+ "WHERE status = :status AND name LIKE CONCAT('%', :name, '%') "
		+ "HAVING distance <= :distance  " //0.1 = 1km, 0.01 = 100m
		+ "ORDER BY distance "
		+ "LIMIT :offset, :limit ", nativeQuery = true)
	List<Restaurant> findAllByNameAndGPSAndDistance(
		@Param(value = "latitude") BigDecimal latitude,
		@Param(value = "longitude") BigDecimal longitude,
		@Param(value = "distance") Float distance,
		@Param(value = "offset") Integer offset,
		@Param(value = "limit") Integer limit,
		@Param(value = "status") String status,
		@Param(value = "name") String name
	);

}
