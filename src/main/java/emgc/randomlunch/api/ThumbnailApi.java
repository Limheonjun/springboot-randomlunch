package emgc.randomlunch.api;

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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public void uploadThumbnail(MultipartFile files[], @RequestPart("restaurantId") Long restaurantId, @RequestPart("hashtags") String hashtags[]) throws IOException {
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
                    .path(path+saveName)
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
}
