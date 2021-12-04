package emgc.randomlunch.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Thumbnail extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "thumbnail_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id")
	private User user;

	@OneToMany(mappedBy = "thumbnail", cascade = CascadeType.ALL)
	private List<ThumbnailHashtag> thumbnailHashtagList = new ArrayList<>();

	private Long size;
	private Integer height;
	private Integer width;
	private Long likes;
	private Long views;
	private String originalFileName;
	private String fileName;
	private String extension;

}
