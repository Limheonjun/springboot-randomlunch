package emgc.randomlunch.api;

import emgc.randomlunch.dto.CategoryInfoDto;
import emgc.randomlunch.dto.RestaurantInfoDto;
import emgc.randomlunch.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantApi {

    private final RestaurantService restaurantService;

    @PostMapping("/upload")
    public void uploadRestaurant(@RequestBody RestaurantInfoDto restaurantInfoDto) {
        restaurantService.addRestaurant(restaurantInfoDto);
    }


    @GetMapping("/list")
    public List<RestaurantInfoDto> getAllRestaurants() {
        return restaurantService.getRestaurantList();
    }

    @GetMapping("/category")
    public List<RestaurantInfoDto> getRestaurantListByCategory(@RequestBody CategoryInfoDto categoryInfoDto) {
        return restaurantService.getRestaurantListByCategory(categoryInfoDto);
    }

    @PostMapping("/edit")
    public void editRestaurant(@RequestBody RestaurantInfoDto restaurantInfoDto) {
        restaurantService.editRestaurant(restaurantInfoDto);
    }

    @DeleteMapping("/delete")
    public void deleteRestaurant(RestaurantInfoDto restaurantInfoDto) {
        restaurantService.deleteRestaurant(restaurantInfoDto);
    }
}
