package emgc.randomlunch.service;

import emgc.randomlunch.dto.CategoryInfoDto;
import emgc.randomlunch.dto.RestaurantInfoDto;
import emgc.randomlunch.entity.Category;
import emgc.randomlunch.entity.Restaurant;
import emgc.randomlunch.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository repository;
    private final CategoryService categoryService;

    //음식점 목록 조회
    public List<RestaurantInfoDto> getRestaurantList() {
        List<Restaurant> findRestaurantList = repository.findAll();
        return findRestaurantList.stream().map(RestaurantInfoDto::new).collect(Collectors.toList());
    }

    //음식점정보 반환
    public Restaurant getRestaurant(RestaurantInfoDto restaurantInfoDto) {
        return getRestaurant(restaurantInfoDto.getId());
    }

    public Restaurant getRestaurant(Long restaurantId) {
        return repository.findById(restaurantId).orElseThrow();
    }

    //카테고리별 음식점 목록 조회
    public List<RestaurantInfoDto> getRestaurantListByCategory(CategoryInfoDto categoryInfoDto) {
        Category category = categoryService.getCategory(categoryInfoDto);
        List<Restaurant> findRestaurants = repository.findByCategory(category);
        return findRestaurants.stream().map(RestaurantInfoDto::new).collect(Collectors.toList());
    }

    //음식점정보 추가
    public Restaurant addRestaurant(RestaurantInfoDto restaurantInfoDto) {
        Category category = categoryService.getCategory(restaurantInfoDto.getCategoryId());
        Restaurant restaurant = Restaurant.builder()
                .name(restaurantInfoDto.getName())
                .openTime(restaurantInfoDto.getOpenTime())
                .closeTime(restaurantInfoDto.getCloseTime())
                .address(restaurantInfoDto.getAddress())
                .category(category)
                .build();
        return repository.save(restaurant);
    }

    //음식점 수정
    public Restaurant editRestaurant(RestaurantInfoDto restaurantInfoDto) {
        Category category = categoryService.getCategory(restaurantInfoDto.getCategoryId());
        Restaurant restaurant = getRestaurant(restaurantInfoDto);
        restaurant.setName(restaurantInfoDto.getName());
        restaurant.setOpenTime(restaurantInfoDto.getOpenTime());
        restaurant.setCloseTime(restaurantInfoDto.getCloseTime());
        restaurant.setAddress(restaurantInfoDto.getAddress());
        restaurant.setCategory(category);
        return restaurant;
    }

    //음식점 삭제
    public void deleteRestaurant(RestaurantInfoDto restaurantInfoDto) {
        Restaurant restaurant = repository.findById(restaurantInfoDto.getId()).orElseThrow();
        repository.delete(restaurant);
    }

}
