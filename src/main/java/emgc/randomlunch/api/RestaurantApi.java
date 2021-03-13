package emgc.randomlunch.api;

import emgc.randomlunch.dto.FileInfoDto;
import emgc.randomlunch.dto.RestaurantInfoDto;
import emgc.randomlunch.entity.Category;
import emgc.randomlunch.entity.Restaurant;
import emgc.randomlunch.repository.CategoryRepository;
import emgc.randomlunch.repository.RestaurantRepository;
import emgc.randomlunch.repository.ThumbnailRepository;
import emgc.randomlunch.service.CategoryService;
import emgc.randomlunch.service.RestaurantService;
import emgc.randomlunch.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantApi {

    @Value("${file.thumbnail.path}")
    private String path;

    private final FileUtil fileUtil;
    private final CategoryService categoryService;
    private final RestaurantService restaurantService;

    //    https://stackoverflow.com/questions/21329426/spring-mvc-multipart-request-with-json
    @PostMapping("/upload")
    public void uploadRestaurant(@RequestPart("files") MultipartFile files[], @RequestPart("restaurant") RestaurantInfoDto restaurantInfoDto) throws IOException {
        Category category = categoryService.getCategory(restaurantInfoDto.getCategoryId());
        List<String> fileName = new ArrayList<>();
        for(MultipartFile file : files) {
            FileInfoDto fileInfoDto = fileUtil.saveFile(file, path);
            fileName.add(fileInfoDto.getName());
        }
        restaurantService.addRestaurant(restaurantInfoDto, category, fileName.get(0));
    }


    @GetMapping("/list")
    public List<RestaurantInfoDto> getAllRestaurants(HttpSession httpSession, HttpServletResponse response) {
        return restaurantService.getRestaurantList();
    }

    @GetMapping("/{categoryId}")
    public List<RestaurantInfoDto> getRestaurantListByCategory(@PathVariable Long categoryId) {
        Category category = categoryService.getCategory(categoryId);
        List<RestaurantInfoDto> restaurants = restaurantService.getRestaurantListByCategory(category);
        return restaurants;
    }

    @PostMapping("/edit")
    public void editRestaurant(@RequestPart("files") MultipartFile files[], @RequestPart("restaurant") RestaurantInfoDto restaurantInfoDto) {
        Category category = categoryService.getCategory(restaurantInfoDto.getCategoryId());
        restaurantService.editRestaurant(restaurantInfoDto, category);
    }

    @DeleteMapping("/delete")
    public void deleteRestaurant(@RequestParam Long restaurantId) {
        restaurantService.deleteRestaurant(restaurantId);
    }
}
