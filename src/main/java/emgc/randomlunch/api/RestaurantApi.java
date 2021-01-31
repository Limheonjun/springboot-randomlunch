package emgc.randomlunch.api;

import emgc.randomlunch.entity.Restaurant;
import emgc.randomlunch.entity.Thumbnail;
import emgc.randomlunch.repository.RestaurantRepository;
import emgc.randomlunch.repository.ThumbnailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantApi {

    @Value("${file.thumbnail.path}")
    private String path;

    private final RestaurantRepository restaurantRepository;
    private final ThumbnailRepository thumbnailRepository;

    //    https://stackoverflow.com/questions/21329426/spring-mvc-multipart-request-with-json
    @PostMapping("/upload")
    public void uploadRestaurant(MultipartFile file, @RequestPart("restaurant")Restaurant restaurant) {
        System.out.println("address : " + restaurant.getAddress());
        System.out.println("name : " + restaurant.getName());
        System.out.println("file : " + file.getOriginalFilename());
        //restaurantRepository.save(restaurant);
        // 파일 이름 변경
        UUID uuid = UUID.randomUUID();
        String saveName = uuid + "_" + LocalDate.now() + "." + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);


        // 저장할 File 객체를 생성(껍데기 파일)
        File saveFile = new File(path,saveName); // 저장할 폴더 이름, 저장할 파일 이름

        try {
            file.transferTo(saveFile); // 업로드 파일에 saveFile이라는 껍데기 입힘
            restaurantRepository.save(restaurant);
            thumbnailRepository.save(Thumbnail.builder()
                                        .id(null)
                                        .path(path+saveName)
                                        .size((int)saveFile.length())
                                        .restaurant(restaurant)
                                        .build()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
