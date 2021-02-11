package emgc.randomlunch.api;

import emgc.randomlunch.dto.RestaurantListDto;
import emgc.randomlunch.entity.Restaurant;
import emgc.randomlunch.entity.Thumbnail;
import emgc.randomlunch.repository.RestaurantRepository;
import emgc.randomlunch.repository.ThumbnailRepository;
import emgc.randomlunch.service.business.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantApi {

    @Value("${file.thumbnail.path}")
    private String path;

    private final RestaurantRepository restaurantRepository;
    private final ThumbnailRepository thumbnailRepository;
    private final RestaurantService restaurantService;

    //    https://stackoverflow.com/questions/21329426/spring-mvc-multipart-request-with-json
    @PostMapping("/upload")
    public void uploadRestaurant(/*MultipartFile files[], */@RequestPart("restaurant")Restaurant restaurant) throws IOException {
        System.out.println("address : " + restaurant.getAddress());
        System.out.println("name : " + restaurant.getName());
    }

    @GetMapping("/list")
    public List<RestaurantListDto> getAllRestaurants() {
        return restaurantService.getAllRestaurnats();
    }
}
