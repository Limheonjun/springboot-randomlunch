package emgc.randomlunch.service;

import emgc.randomlunch.dto.CategoryInfoDto;
import emgc.randomlunch.dto.RestaurantInfoDto;
import emgc.randomlunch.entity.Category;
import emgc.randomlunch.entity.Restaurant;
import emgc.randomlunch.repository.RestaurantRepository;
import emgc.randomlunch.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository repository;
    private final FileUtil fileUtil;

    //음식점 목록 조회
    public List<RestaurantInfoDto> getRestaurantList() {
        List<RestaurantInfoDto> restaurantList = new ArrayList<>();
        List<Restaurant> findRestaurantList = repository.findAll();
        restaurantList = findRestaurantList.stream()
                .map(RestaurantInfoDto::new)
                .collect(Collectors.toList());
        return restaurantList;
    }

    //음식점정보 반환
    public Restaurant getRestaurant(Long id) {
        Restaurant restaurant = repository.findById(id).orElseThrow();
        return restaurant;
    }

    //카테고리별 음식점 목록 조회
    public List<RestaurantInfoDto> getRestaurantListByCategory(Category category) {
        List<RestaurantInfoDto> restaurantList = new ArrayList<>();
        List<Restaurant> findRestaurants = repository.findByCategory(category);
        restaurantList = findRestaurants.stream()
                .map(RestaurantInfoDto::new)
                .collect(Collectors.toList());
        return restaurantList;
    }

    //음식점정보 추가
    public void addRestaurant(RestaurantInfoDto restaurantInfoDto, Category category, String fileName) throws IOException {

        Restaurant restaurant = Restaurant.builder()
                .name(restaurantInfoDto.getName())
                .openTime(restaurantInfoDto.getOpenTime())
                .closeTime(restaurantInfoDto.getCloseTime())
                .fileName(fileName)
                .address(restaurantInfoDto.getAddress())
                .category(category)
                .build();

        repository.save(restaurant);
    }

    //음식점 수정
    public void editRestaurant(RestaurantInfoDto restaurantInfoDto, Category category) {
        Restaurant restaurant = repository.findById(restaurantInfoDto.getId()).orElseThrow();
        restaurant.setName(restaurantInfoDto.getName());
        restaurant.setOpenTime(restaurantInfoDto.getOpenTime());
        restaurant.setCloseTime(restaurantInfoDto.getCloseTime());
        restaurant.setAddress(restaurantInfoDto.getAddress());
        restaurant.setCategory(category);
        repository.save(restaurant);
    }

    //카테고리 삭제
    public void deleteRestaurant(Long id) {
        Restaurant restaurant = repository.findById(id).orElseThrow();
        repository.delete(restaurant);
    }



}
