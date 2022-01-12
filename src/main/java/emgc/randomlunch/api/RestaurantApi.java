package emgc.randomlunch.api;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import emgc.randomlunch.dto.restaurant.RestaurantResponse;
import emgc.randomlunch.service.function.RestaurantService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("restaurants")
@RequiredArgsConstructor
public class RestaurantApi {

	private final RestaurantService restaurantService;

	@GetMapping("")
	public List<RestaurantResponse> getRestaurantList(
		@RequestParam String restaurantName,
		Pageable pageable
	) {
		return restaurantService.getRestaurantList(restaurantName, (PageRequest)pageable);
	}

}
