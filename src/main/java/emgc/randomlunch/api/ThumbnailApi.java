package emgc.randomlunch.api;

import emgc.randomlunch.dto.ThumbnailInfoDto;
import emgc.randomlunch.entity.Hashtag;
import emgc.randomlunch.entity.Restaurant;
import emgc.randomlunch.entity.Thumbnail;
import emgc.randomlunch.entity.ThumbnailHashtag;
import emgc.randomlunch.repository.HashtagRepository;
import emgc.randomlunch.repository.RestaurantRepository;
import emgc.randomlunch.repository.ThumbnailHashtagRepository;
import emgc.randomlunch.repository.ThumbnailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
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

    @Value("${file.thumbnail.path}")
    private String path;

    @PostMapping("/upload")
    public void uploadThumbnail(MultipartFile files[],
                                @RequestPart("restaurantId") Long restaurantId,
                                @RequestPart("hashtags") String hashtags[],
                                BindingResult bindingResult) throws IOException {
        if(bindingResult.hasErrors()) {
            bindingResult.getFieldErrors();
            return;
        }
        List<Thumbnail> thumbnailList = new ArrayList<>();
        List<Hashtag> hashtagList = new ArrayList<>();

        // TODO : 음식점이 선택되지 않았을 경우 에러처리
        Restaurant restaurant = restaurantRepository.findById(restaurantId).get();

        for (String hashtag : hashtags) {
            // TODO : DB에 해시태그가 존재하는지 확인 후 있는 경우엔 DB에서 꺼내오기
            Hashtag saveHashtag = hashtagRepository.save(Hashtag.builder().id(null).word(hashtag).build());
            hashtagList.add(saveHashtag);
        }

        for (MultipartFile file : files) {

            BufferedImage image = ImageIO.read(file.getInputStream());
            int width = image.getWidth();
            int height = image.getHeight();

            // 파일 이름 변경
            UUID uuid = UUID.randomUUID();
            String saveName = uuid + "_" + LocalDate.now() + "." + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            // 저장할 File 객체를 생성(껍데기 파일)
            File saveFile = new File(path, saveName); // 저장할 폴더 이름, 저장할 파일 이름
            file.transferTo(saveFile); // 업로드 파일에 saveFile이라는 껍데기 입힘

            Thumbnail thumbnail = Thumbnail.builder()
                    .id(null)
                    .restaurant(restaurant)
                    .size(saveFile.length())
                    .thumbnailHeight(width)
                    .thumbnailHeight(height)
//                    .path(path+saveName)
                    .fileName(saveName)
                    .build();

            Thumbnail saveThumbnail = thumbnailRepository.save(thumbnail);
            thumbnailList.add(saveThumbnail);
        }

        for (Thumbnail thumbnail : thumbnailList) {
            for (Hashtag hashtag : hashtagList) {
                ThumbnailHashtag thumbnailHashtag = ThumbnailHashtag.builder()
                        .hashtag(hashtag)
                        .thumbnail(thumbnail)
                        .build();

                thumbnailHashtagRepository.save(thumbnailHashtag);
            }
        }
    }

    @GetMapping("/list")
    public List<ThumbnailInfoDto> getAllThumbnails(int pageNumber) {
        System.out.println("pageNum : " + pageNumber);
        Page<Thumbnail> all = thumbnailRepository.findAll(PageRequest.of(pageNumber, 25));
//        all.stream().map(PostDTO::new).collect(Collectors.toList());
//        List<Thumbnail> thumbnailList = (List<Thumbnail>) all;

        List<ThumbnailInfoDto> thumbnailInfoDtoList = new ArrayList<>();

        // TODO : thumbnail을 가져올때 hash를 1+n으로 가져오는 현상 eager처리로 변경 요망
        for(Thumbnail thumbnail : all) {
            ThumbnailInfoDto thumbnailInfoDto = ThumbnailInfoDto.builder()
                    .thumbnailId(thumbnail.getId())
                    .restaurantId(thumbnail.getRestaurant().getId())
                    .restaurantName(thumbnail.getRestaurant().getName())
                    .thumbnailHeight(thumbnail.getThumbnailHeight())
//                    .path(thumbnail.getPath())
                    .fileName(thumbnail.getFileName())
                    .hashtags(thumbnail.getThumbnailHashtagList()
                            .stream()
                            .map(hashtag -> hashtag.getHashtag().getWord())
                            .collect(Collectors.toList())
                    )
                    .build();

            thumbnailInfoDtoList.add(thumbnailInfoDto);
        }
        Collections.shuffle(thumbnailInfoDtoList);

        return thumbnailInfoDtoList;
    }

    @GetMapping("/carousel")
    public List<ThumbnailInfoDto> getRandomThumbnails(@RequestParam Long restaurantId) {
        Restaurant findRestaurant = restaurantRepository.findById(restaurantId).get();
        List<Thumbnail> thumbnailList = thumbnailRepository.findFirst8ByRestaurant(findRestaurant);
        Collections.shuffle(thumbnailList);
        List<ThumbnailInfoDto> thumbnailInfoDtoList = new ArrayList<>();

        // TODO : thumbnail을 가져올때 hash를 1+n으로 가져오는 현상 eager처리로 변경 요망
        for(Thumbnail thumbnail : thumbnailList) {
            ThumbnailInfoDto thumbnailInfoDto = ThumbnailInfoDto.builder()
                    .thumbnailId(thumbnail.getId())
                    .restaurantId(thumbnail.getRestaurant().getId())
                    .restaurantName(thumbnail.getRestaurant().getName())
                    .thumbnailHeight(thumbnail.getThumbnailHeight())
//                    .path(thumbnail.getPath())
                    .fileName(thumbnail.getFileName())
                    .hashtags(thumbnail.getThumbnailHashtagList()
                            .stream()
                            .map(hashtag -> hashtag.getHashtag().getWord())
                            .collect(Collectors.toList())
                    )
                    .build();

            thumbnailInfoDtoList.add(thumbnailInfoDto);
        }

        return thumbnailInfoDtoList;
    }

    // TODO : 썸네일 정보 조회 api 추가 요망
}
