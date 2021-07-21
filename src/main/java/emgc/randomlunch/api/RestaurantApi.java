package emgc.randomlunch.api;

import emgc.randomlunch.dto.RestaurantInfoDto;
import emgc.randomlunch.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant")
@Slf4j
@Validated
public class RestaurantApi {

    private final RestaurantService restaurantService;

    @PostMapping("/upload")
    public void uploadRestaurant(
            @RequestBody @Valid RestaurantInfoDto restaurantInfoDto,
            @NotNull(message = "음식점 추가는 로그인이 필요합니다.") Principal principal
    ) {
        log.info("Received a request to create a restaurant {} from user {}", restaurantInfoDto.getName(), principal.getName());
        restaurantService.addRestaurant(restaurantInfoDto);
    }


    @GetMapping("/list")
    public List<RestaurantInfoDto> getAllRestaurants() {
        log.info("Retrieving restaurants");
        return restaurantService.getRestaurantList();
    }

    @GetMapping("/list/{id}")
    public List<RestaurantInfoDto> getRestaurantListByCategory(
            @PathVariable
            @NotNull(message = "조회할 음식점의 카테고리 아이디는 필수값 입니다.")
            @Positive(message = "조회할 음식점의 카테고리 아이디는 양의 정수이어야 합니다.")
            @Min(value = 1L, message = "조회할 음식점의 카테고리 아이디는 최소 1 이상이어야 합니다.") Long id
    ) {
        log.info("Retrieving restaurants for category {}", id);
        return restaurantService.getRestaurantListByCategory(id);
    }

    @PostMapping("/edit")
    public void editRestaurant(
            @RequestBody @Valid RestaurantInfoDto restaurantInfoDto,
            @NotNull(message = "음식점 수정은 로그인이 필요합니다.") Principal principal
    ) {
        log.info("Received a edit request for restaurant id {} from user {}", restaurantInfoDto.getId(), principal.getName());
        restaurantService.editRestaurant(restaurantInfoDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteRestaurant(
            @PathVariable Long id,
            @NotNull(message = "음식점 삭제는 로그인이 필요합니다.") Principal principal
    ) {
        log.info("Received a delete request for restaurant id {} from user {}", id, principal.getName());
        restaurantService.deleteRestaurant(id);
    }
}
