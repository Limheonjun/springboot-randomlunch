package emgc.randomlunch.dto;

import emgc.randomlunch.entity.Restaurant;
import emgc.randomlunch.entity.ThumbnailHashtag;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ThumbnailInfoDto {

    private Long id;
    private Restaurant restaurant;
    private int size;
    private int thumbnailHeight;
    private int thumbnailWidth;
    private String path;

}
