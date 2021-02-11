package emgc.randomlunch.dto;

import emgc.randomlunch.entity.Thumbnail;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThumbnailListDto {

    private Long thumbnailId;
    private Long restaurantId;
    private String thumbnailPath;
    private Long thumbnailSize;
    private int thumbnailHeight;
    private int thumbnailWidth;

    public ThumbnailListDto(Thumbnail thumbnail) {
        thumbnailId = thumbnail.getId();
        restaurantId = thumbnail.getRestaurant().getId();
        thumbnailPath = thumbnail.getPath();
        thumbnailSize = thumbnail.getSize();
        thumbnailHeight = thumbnail.getThumbnailHeight();
        thumbnailWidth = thumbnail.getThumbnailWidth();

    }

}
