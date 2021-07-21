package emgc.randomlunch.dto;

import emgc.randomlunch.entity.Restaurant;
import emgc.randomlunch.entity.Thumbnail;
import emgc.randomlunch.entity.ThumbnailHashtag;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantInfoDto {

    private Long id;
    private List<String> menuList = new ArrayList<>();

    @NotBlank(message = "음식점 이름은 필수값 입니다.")
    @Size(min = 1, max = 20, message = "음식점 이름은 1~20자 이어야 합니다.")
    private String name;

    @NotNull(message = "음식점 개점 시간은 필수값 입니다.")
    private LocalTime openTime;


    @NotNull(message = "음식점 폐점 시간은 필수값 입니다.")
    private LocalTime closeTime;

    @NotNull(message = "음식점이 속한 카테고리의 아이디는 필수값 입니다.")
    @Positive(message = "음식점이 속한 카테고리의 아이디는 양의 정수이어야 합니다.")
    @Min(value = 1L, message = "음식점이 속한 카테고리의 아이디는 최소 1 이상이어야 합니다.")
    private Long categoryId;

    @NotBlank(message = "음식점의 주소는 필수값 입니다.")
    private String address;
    private List<String> hashtags = new ArrayList<>();

    public void setOpenTime(String openTime) {
        this.openTime = LocalTime.parse(openTime);
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = LocalTime.parse(closeTime);
    }

    public RestaurantInfoDto(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.openTime = restaurant.getOpenTime();
        this.closeTime = restaurant.getCloseTime();
        this.address = restaurant.getAddress();
        this.categoryId = restaurant.getCategory().getId();
        this.hashtags = getRandomHashtags(restaurant);
    }

    private List<String> getRandomHashtags(Restaurant restaurant) {
        List<String> hashtags = new ArrayList<>();

        List<Thumbnail> thumbnailList = restaurant.getThumbnailList();
        if (thumbnailList != null) {
            Collections.shuffle(thumbnailList);
            Loop1:
            for (Thumbnail thumbnail : thumbnailList) {
                List<ThumbnailHashtag> thumbnailHashtagList = thumbnail.getThumbnailHashtagList();
                Loop2:
                for (ThumbnailHashtag thumbnailHashtag : thumbnailHashtagList) {
                    String word = thumbnailHashtag.getHashtag().getWord();
                    hashtags.add(word);
                    if(hashtags.size() > 0) break Loop1;
                }

            }
        }
        return hashtags;
    }
}
