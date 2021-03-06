package emgc.randomlunch.api;

import emgc.randomlunch.dto.CategoryInfoDto;
import emgc.randomlunch.dto.RestaurantInfoDto;
import emgc.randomlunch.dto.RestaurantListDto;
import emgc.randomlunch.entity.Category;
import emgc.randomlunch.entity.Menu;
import emgc.randomlunch.entity.Restaurant;
import emgc.randomlunch.entity.Thumbnail;
import emgc.randomlunch.repository.CategoryRepository;
import emgc.randomlunch.repository.RestaurantRepository;
import emgc.randomlunch.repository.ThumbnailRepository;
import emgc.randomlunch.service.business.RestaurantService;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantApi {

    @Value("${file.thumbnail.path}")
    private String path;

    private final RestaurantRepository restaurantRepository;
    private final ThumbnailRepository thumbnailRepository;
    private final RestaurantService restaurantService;
    private final CategoryRepository categoryRepository;

    //    https://stackoverflow.com/questions/21329426/spring-mvc-multipart-request-with-json
    @PostMapping("/upload")
    public void uploadRestaurant(@RequestPart("files") MultipartFile files[], @RequestPart("restaurant") RestaurantInfoDto restaurantInfoDto) throws IOException {
        System.out.println("address : " + restaurantInfoDto.getAddress());
        System.out.println("name : " + restaurantInfoDto.getName());

        String saveName = null;
        if(files != null) {
            for (MultipartFile file : files) {

                BufferedImage image = ImageIO.read(file.getInputStream());
                int width = image.getWidth();
                int height = image.getHeight();

                // 파일 이름 변경
                UUID uuid = UUID.randomUUID();
                saveName = uuid + "_" + LocalDate.now() + "." + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
                // 저장할 File 객체를 생성(껍데기 파일)
                File saveFile = new File(path, saveName); // 저장할 폴더 이름, 저장할 파일 이름
                file.transferTo(saveFile); // 업로드 파일에 saveFile이라는 껍데기 입힘

            }
        }

        Category findCategory = categoryRepository.findById(restaurantInfoDto.getCategoryId()).get();

        Restaurant restaurant = Restaurant.builder()
                .address(restaurantInfoDto.getAddress())
                .name(restaurantInfoDto.getName())
                .openTime(restaurantInfoDto.getOpenTime())
                .closeTime(restaurantInfoDto.getCloseTime())
                .category(findCategory)
                .fileName(saveName)
                .build();


        restaurantRepository.save(restaurant);
    }


    @GetMapping("/list")
    public List<RestaurantInfoDto> getAllRestaurants(HttpSession httpSession, HttpServletResponse response) {
        List<Restaurant> all = restaurantRepository.findAll();
        List<RestaurantInfoDto> result = all.stream().map(RestaurantInfoDto::new).collect(Collectors.toList());
        return result;
    }

    @GetMapping("/{categoryId}")
    public List<RestaurantInfoDto> getRestaourant(@PathVariable Long categoryId) {
        System.out.println("categoryId : " + categoryId);
        Category findCategory = categoryRepository.findById(categoryId).get();
        List<Restaurant> restaurants = restaurantRepository.findByCategory(findCategory);
        List<RestaurantInfoDto> restaurantListDtoList = restaurants
                .stream()
                .map(restaurant -> new RestaurantInfoDto(restaurant))
                .collect(Collectors.toList());
        return restaurantListDtoList;
    }

    @PostMapping("/edit")
    public void editRestaurant(@RequestPart("files") MultipartFile files[], @RequestPart("restaurant") RestaurantInfoDto restaurantInfoDto) throws IOException {

        Restaurant findRestaurant = restaurantRepository.findById(restaurantInfoDto.getRestaurantId()).get();

        String saveName = null;

        if(files != null) {
            for (MultipartFile file : files) {

                BufferedImage image = ImageIO.read(file.getInputStream());
                int width = image.getWidth();
                int height = image.getHeight();

                // 파일 이름 변경
                UUID uuid = UUID.randomUUID();
                saveName = uuid + "_" + LocalDate.now() + "." + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
                // 저장할 File 객체를 생성(껍데기 파일)
                File saveFile = new File(path, saveName); // 저장할 폴더 이름, 저장할 파일 이름
                file.transferTo(saveFile); // 업로드 파일에 saveFile이라는 껍데기 입힘

            }
        }

        Category findCategory = categoryRepository.findById(restaurantInfoDto.getCategoryId()).get();

        findRestaurant.setAddress(restaurantInfoDto.getAddress());
        findRestaurant.setName(restaurantInfoDto.getName());
        findRestaurant.setOpenTime(restaurantInfoDto.getOpenTime());
        findRestaurant.setCloseTime(restaurantInfoDto.getCloseTime());
        findRestaurant.setCategory(findCategory);
        findRestaurant.setFileName(saveName);
//
//        Restaurant restaurant = Restaurant.builder()
//                .address(restaurantInfoDto.getAddress())
//                .name(restaurantInfoDto.getName())
//                .openTime(restaurantInfoDto.getOpenTime())
//                .closeTime(restaurantInfoDto.getCloseTime())
//                .category(findCategory)
//                .fileName(saveName)
//                .build();


        restaurantRepository.save(findRestaurant);
    }

    @DeleteMapping("/delete")
    public void deleteRestaurant(@RequestParam Long restaurantId) {
        Restaurant findRestaurant = restaurantRepository.findById(restaurantId).get();
        restaurantRepository.delete(findRestaurant);
    }
}
