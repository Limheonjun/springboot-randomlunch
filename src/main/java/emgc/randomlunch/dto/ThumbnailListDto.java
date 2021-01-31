package emgc.randomlunch.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThumbnailListDto {

    private Long thumbnailId;
    private Long restaurantId;
    private String path;
    private String hashtag;
    private String name;

}
