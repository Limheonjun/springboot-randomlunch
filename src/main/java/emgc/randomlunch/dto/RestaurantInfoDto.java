package emgc.randomlunch.dto;

import emgc.randomlunch.entity.Restaurant;
import emgc.randomlunch.entity.Thumbnail;
import emgc.randomlunch.entity.ThumbnailHashtag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantInfoDto {

    private Long id;
    private List<String> menuList = new ArrayList<>();
    private List<Integer> priceList = new ArrayList<>();
    private String name;
    private String locationX;
    private String locationY;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Long categoryId;
    private String fileName;
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
        this.fileName = restaurant.getFileName();
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
