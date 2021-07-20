package emgc.randomlunch.service;

import emgc.randomlunch.dto.RestaurantInfoDto;
import emgc.randomlunch.dto.ThumbnailInfoDto;
import emgc.randomlunch.entity.File;
import emgc.randomlunch.entity.Hashtag;
import emgc.randomlunch.entity.Restaurant;
import emgc.randomlunch.entity.Thumbnail;
import emgc.randomlunch.repository.ThumbnailRepository;
import emgc.randomlunch.security.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ThumbnailService {

    private final ThumbnailRepository repository;
    private final UserService userService;
    private final RestaurantService restaurantService;
    private final HashtagService hashtagService;
    private final FileService fileService;
    private final ThumbnailHashtagService thumbnailHashtagService;

    @Value("${file.thumbnail.path}")
    private String path;

    @Transactional
    public void uploadThumbnailWithHashtag(MultipartFile files[], RestaurantInfoDto restaurantInfoDto) throws IOException {
        Restaurant restaurant = restaurantService.getRestaurant(restaurantInfoDto.getId());
        List<Hashtag> hashtags = hashtagService.addHashtag(restaurantInfoDto.getHashtags().toArray(String[]::new));
        List<File> fileList = fileService.addFileList(files, path);
        List<Thumbnail> thumbnailList = addThumbnailList(fileList, restaurant);
        thumbnailHashtagService.addThumbnailHashtagList(thumbnailList, hashtags);
    }

    // 썸네일 목록 조회
    public List<ThumbnailInfoDto> getThumbnailList(Restaurant restaurant, Pageable pageable) {
        List<Thumbnail> thumbnails = repository.findByRestaurant(restaurant, pageable);
        return thumbnails.stream().map(ThumbnailInfoDto::new).collect(Collectors.toList());
    }

    public List<ThumbnailInfoDto> getThumbnailList(Pageable pageable){
        List<Thumbnail> thumbnails = repository.findAll(pageable).getContent();
        return thumbnails.stream().map(ThumbnailInfoDto::new).collect(Collectors.toList());
    }

    public List<ThumbnailInfoDto> getThumbnailListByUser(Pageable pageable, Long id){
        User user = userService.getUser(id);
        List<Thumbnail> thumbnails = repository.findByUser(user, pageable);
        return thumbnails.stream().map(ThumbnailInfoDto::new).collect(Collectors.toList());
    }

    public List<Thumbnail> addThumbnailList(File[] files, Restaurant restaurant) {
        return addThumbnailList(Arrays.stream(files).collect(Collectors.toList()), restaurant);
    }

    //썸네일 추가
    public List<Thumbnail> addThumbnailList(List<File> files, Restaurant restaurant) {
        List<Thumbnail> thumbnailList = new ArrayList<>();
        for(File file : files) {
            thumbnailList.add(addThumbnail(file, restaurant));
        }
        return thumbnailList;
    }

    public Thumbnail addThumbnail(File file, Restaurant restaurant) {
        Thumbnail thumbnail = Thumbnail.builder()
                .restaurant(restaurant)
                .file(file)
                .build();
        return repository.save(thumbnail);
    }

    // 캐러셀 8개 가져와서 섞기
    public List<ThumbnailInfoDto> getRandom8Thumbnail(Restaurant restaurant) {
        List<Thumbnail> findCarousel = repository.findFirst8ByRestaurant(restaurant);
        Collections.shuffle(findCarousel);
        return findCarousel.stream().map(ThumbnailInfoDto::new).collect(Collectors.toList());
    }


}
