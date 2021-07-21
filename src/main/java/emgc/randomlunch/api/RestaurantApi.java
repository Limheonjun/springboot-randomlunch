package emgc.randomlunch.api;

import emgc.randomlunch.dto.CategoryInfoDto;
import emgc.randomlunch.dto.RestaurantInfoDto;
import emgc.randomlunch.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant")
@Slf4j
public class RestaurantApi {

    private final RestaurantService restaurantService;

    @PostMapping("/upload")
    public void uploadRestaurant(@RequestBody RestaurantInfoDto restaurantInfoDto, Principal principal) {
        log.info("Received a request to create a restaurant {} from user {}", restaurantInfoDto.getName(), principal.getName());
        restaurantService.addRestaurant(restaurantInfoDto);
    }


    @GetMapping("/list")
    public List<RestaurantInfoDto> getAllRestaurants() {
        log.info("Retrieving restaurants");
        return restaurantService.getRestaurantList();
    }

    @GetMapping("/list/{id}")
    public List<RestaurantInfoDto> getRestaurantListByCategory(@PathVariable("id") Long categoryId) {
        log.info("Retrieving restaurants for category {}", categoryId);
        return restaurantService.getRestaurantListByCategory(categoryId);
    }

    @PostMapping("/edit")
    public void editRestaurant(@RequestBody RestaurantInfoDto restaurantInfoDto, Principal principal) {
        log.info("Received a edit request for restaurant id {} from user {}", restaurantInfoDto.getId(), principal.getName());
        restaurantService.editRestaurant(restaurantInfoDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteRestaurant(@PathVariable Long id, Principal principal) {
        log.info("Received a delete request for restaurant id {} from user {}", id, principal.getName());
        restaurantService.deleteRestaurant(id);
    }
}
