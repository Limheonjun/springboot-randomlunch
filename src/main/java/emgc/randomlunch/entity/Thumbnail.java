package emgc.randomlunch.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
public class Thumbnail {

    @Id
    @GeneratedValue
    @Column(name = "thumbnail_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    private Long size;
    private int thumbnailHeight;
    private int thumbnailWidth;
    private String path;

    @OneToMany(mappedBy = "thumbnail", cascade = CascadeType.ALL)
    private List<ThumbnailHashtag> thumbnailHashtagList = new ArrayList<>();
}
