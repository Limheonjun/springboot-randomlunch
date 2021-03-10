package emgc.randomlunch.service;

import emgc.randomlunch.entity.Restaurant;
import emgc.randomlunch.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository repository;

    //음식점정보 반환
    public Restaurant getRestaurant(Long id) {
        Restaurant restaurant = repository.findById(id).orElseThrow();
        return restaurant;
    }

}
