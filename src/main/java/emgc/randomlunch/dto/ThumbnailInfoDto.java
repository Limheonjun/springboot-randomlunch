package emgc.randomlunch.dto;

import emgc.randomlunch.entity.Hashtag;
import emgc.randomlunch.entity.Thumbnail;
import emgc.randomlunch.entity.ThumbnailHashtag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ThumbnailInfoDto {

    private Long id;
    private Long restaurantId;
    private String restaurantName;
    private Long fileId;
    private String fileName;
    private List<String> hashtags = new ArrayList<>();

    public ThumbnailInfoDto(Thumbnail thumbnail){
        this.id = thumbnail.getId();
        this.restaurantId = thumbnail.getRestaurant().getId();
        this.restaurantName = thumbnail.getRestaurant().getName();
        this.fileId = thumbnail.getFile().getId();
        this.fileName = thumbnail.getFile().getName();
        this.hashtags = thumbnail.getThumbnailHashtagList().stream()
                .map(ThumbnailHashtag::getHashtag)
                .map(Hashtag::getWord)
                .collect(Collectors.toList());
    }

}
