package emgc.randomlunch.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Thumbnail {

    @Id
    @GeneratedValue
    @Column(name = "thumbnail_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private File file;

    @OneToMany(mappedBy = "thumbnail", cascade = CascadeType.ALL)
    private List<ThumbnailHashtag> thumbnailHashtagList = new ArrayList<>();
}
