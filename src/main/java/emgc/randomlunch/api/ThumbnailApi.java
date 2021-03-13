package emgc.randomlunch.api;

import emgc.randomlunch.dto.FileInfoDto;
import emgc.randomlunch.dto.ThumbnailInfoDto;
import emgc.randomlunch.entity.Hashtag;
import emgc.randomlunch.entity.Restaurant;
import emgc.randomlunch.entity.Thumbnail;
import emgc.randomlunch.entity.ThumbnailHashtag;
import emgc.randomlunch.repository.HashtagRepository;
import emgc.randomlunch.repository.RestaurantRepository;
import emgc.randomlunch.repository.ThumbnailHashtagRepository;
import emgc.randomlunch.repository.ThumbnailRepository;
import emgc.randomlunch.service.*;
import emgc.randomlunch.entity.File;
import emgc.randomlunch.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/thumbnail")
public class ThumbnailApi {

    private final ThumbnailRepository thumbnailRepository;
    private final RestaurantRepository restaurantRepository;
    private final HashtagRepository hashtagRepository;
    private final ThumbnailHashtagRepository thumbnailHashtagRepository;
    private final ThumbnailService thumbnailService;
    private final RestaurantService restaurantService;
    private final HashtagService hashtagService;
    private final FileService fileService;
    private final ThumbnailHashtagService thumbnailHashtagService;
    private final FileUtil fileUtil;

    @Value("${file.thumbnail.path}")
    private String path;

    @PostMapping("/upload")
    public void uploadThumbnailAndHashtag(MultipartFile files[],
                                @RequestPart("restaurantId") Long restaurantId,
                                @RequestPart("hashtags") String hashtags[]
                                ) throws IOException {

        List<Thumbnail> thumbnailList = new ArrayList<>();
        List<File> fileList = new ArrayList<>();
        Restaurant restaurant = restaurantService.getRestaurant(restaurantId);

        // 해시태그 저장 및 Hashtag엔티티 생성
        List<Hashtag> hashtagList = hashtagService.addHashtag(hashtags);

        // 파일 저장 및 File엔티티 저장
        for(MultipartFile file : files) {
            FileInfoDto fileInfoDto = fileUtil.saveFile(file, path); // 실제 파일 저장
            File addFile = File.builder()
                    .name(fileInfoDto.getName())
                    .height(fileInfoDto.getHeight())
                    .width(fileInfoDto.getWidth())
                    .extension(fileInfoDto.getExtension())
                    .size(fileInfoDto.getSize())
                    .build();
            File saveFile = fileService.addFile(addFile);
            fileList.add(saveFile);
        }

        // 썸네일 저장
        for(File file : fileList) {
            thumbnailList = thumbnailService.addThumbnail(file, restaurant);
        }

        // 썸네일 해시태그 연결
        for (Thumbnail thumbnail : thumbnailList) {
            for (Hashtag hashtag : hashtagList) {
                thumbnailHashtagService.addThumnailHashtag(thumbnail, hashtag);
            }
        }
    }

    @GetMapping("/list")
    public List<ThumbnailInfoDto> getAllThumbnails(int pageNumber) {
        return thumbnailService.getThumbnailList(pageNumber);
    }

    @GetMapping("/carousel")
    public List<ThumbnailInfoDto> getCarousel(@RequestParam Long restaurantId) {
        Restaurant findRestaurant = restaurantService.getRestaurant(restaurantId);
        List<ThumbnailInfoDto> carousel = thumbnailService.getRandom8Thumbnail(findRestaurant);
        return carousel;
    }

    // TODO : 썸네일 정보 조회 api 추가 요망
}
