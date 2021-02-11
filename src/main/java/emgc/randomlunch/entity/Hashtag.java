package emgc.randomlunch.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
public class Hashtag {

    @Id
    @GeneratedValue
    @Column(name = "hashtag_id")
    private Long id;

    private String word;

    @OneToMany(mappedBy = "hashtag", cascade = CascadeType.ALL)
    private List<ThumbnailHashtag> thumbnailHashtagList = new ArrayList<>();

}
