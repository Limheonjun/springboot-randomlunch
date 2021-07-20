package emgc.randomlunch.api;

import emgc.randomlunch.dto.CategoryInfoDto;
import emgc.randomlunch.dto.RestaurantInfoDto;
import emgc.randomlunch.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant")
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

    @GetMapping("/list/{id}")
    public List<RestaurantInfoDto> getRestaurantListByCategory(@PathVariable("id") Long categoryId) {
        return restaurantService.getRestaurantListByCategory(categoryId);
    }

    @PostMapping("/edit")
    public void editRestaurant(@RequestBody RestaurantInfoDto restaurantInfoDto) {
        restaurantService.editRestaurant(restaurantInfoDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
    }
}
