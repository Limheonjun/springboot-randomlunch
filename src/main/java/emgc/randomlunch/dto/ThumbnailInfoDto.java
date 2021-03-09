package emgc.randomlunch.dto;

import emgc.randomlunch.entity.Restaurant;
import emgc.randomlunch.entity.ThumbnailHashtag;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class ThumbnailInfoDto {

    private Long id;
    private Long restaurantId;
    private String restaurantName;
    private Long size;
    private int thumbnailHeight;
    private int thumbnailWidth;
    private String fileName;
    private List<String> hashtags = new ArrayList<>();

}
