package emgc.randomlunch.api;

import emgc.randomlunch.dto.RestaurantInfoDto;
import emgc.randomlunch.dto.ThumbnailInfoDto;
import emgc.randomlunch.entity.File;
import emgc.randomlunch.entity.Hashtag;
import emgc.randomlunch.entity.Restaurant;
import emgc.randomlunch.entity.Thumbnail;
import emgc.randomlunch.security.domain.User;
import emgc.randomlunch.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/thumbnail")
public class ThumbnailApi {

    private final ThumbnailService thumbnailService;
    private final RestaurantService restaurantService;
    private final HashtagService hashtagService;
    private final FileService fileService;
    private final ThumbnailHashtagService thumbnailHashtagService;

    @PostMapping("/upload")
    public void uploadThumbnailWithHashtag(@RequestPart("files") MultipartFile files[],
                                           @RequestPart("restaurant") RestaurantInfoDto restaurantInfoDto) throws IOException {
        thumbnailService.uploadThumbnailWithHashtag(files, restaurantInfoDto);
    }
    @GetMapping("/list")
    public List<ThumbnailInfoDto> getThumbnailList(Pageable pageable) {
        return thumbnailService.getThumbnailList(pageable);
    }

    @GetMapping("/list/{id}")
    public List<ThumbnailInfoDto> getThumbnailListByUser(Pageable pageable, @PathVariable Long id) {
        return thumbnailService.getThumbnailListByUser(pageable, id);
    }

    @GetMapping("/restaurant")
    public List<ThumbnailInfoDto> getThumbnailListByRestaurant(Pageable pageable, @RequestBody RestaurantInfoDto restaurantInfoDto) {
        Restaurant restaurant = restaurantService.getRestaurant(restaurantInfoDto);
        return thumbnailService.getThumbnailList(restaurant, pageable);
    }

    @GetMapping("/carousel/{restaurantId}")
    public List<ThumbnailInfoDto> getCarousel(@PathVariable Long restaurantId) {
        Restaurant findRestaurant = restaurantService.getRestaurant(restaurantId);
        List<ThumbnailInfoDto> carousel = thumbnailService.getRandom8Thumbnail(findRestaurant);
        return carousel;
    }
}
