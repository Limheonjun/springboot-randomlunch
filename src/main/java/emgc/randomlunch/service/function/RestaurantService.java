package emgc.randomlunch.service.function;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import emgc.randomlunch.dto.restaurant.RestaurantResponse;

public interface RestaurantService {

	List<RestaurantResponse> getRestaurantList(String restaurantName, PageRequest pageRequest);

}
