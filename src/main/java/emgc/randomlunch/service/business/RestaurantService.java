package emgc.randomlunch.service.business;

import emgc.randomlunch.dto.RestaurantListDto;
import emgc.randomlunch.entity.Restaurant;
import emgc.randomlunch.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    
    private final RestaurantRepository repository;
    
    //음식점 목록
    public List<RestaurantListDto> getAllRestaurnats() {
        List<Restaurant> restaurantList = repository.findAll();
        List<RestaurantListDto> restaurantListDtoList = restaurantList
                                                        .stream()
                                                        .map(restaurant -> new RestaurantListDto(restaurant))
                                                        .collect(Collectors.toList());
        
        return restaurantListDtoList;
    }
    
}
