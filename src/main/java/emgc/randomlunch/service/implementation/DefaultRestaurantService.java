package emgc.randomlunch.service.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import emgc.randomlunch.dto.restaurant.RestaurantResponse;
import emgc.randomlunch.entity.Restaurant;
import emgc.randomlunch.repository.RestaurantRepository;
import emgc.randomlunch.service.function.RestaurantService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefaultRestaurantService implements RestaurantService {

	private final RestaurantRepository restaurantRepository;

	@Override
	public List<RestaurantResponse> getRestaurantList(String restaurantName, PageRequest pageRequest) {
		List<Restaurant> restaurantList = restaurantRepository.findAllByNameLike(
			restaurantName,
			pageRequest.getPageNumber(),
			pageRequest.getPageSize());

		List<RestaurantResponse> restaurantResponsesList = restaurantList.stream()
			.map(RestaurantResponse::from)
			.collect(Collectors.toList());
		return restaurantResponsesList;
	}
}
