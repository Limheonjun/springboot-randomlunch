package emgc.randomlunch.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThumbnailHashtag extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "thumbnailHashtag_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "thumbnail_id")
	private Thumbnail thumbnail;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hashtag_id")
	private Hashtag hashtag;

	public static ThumbnailHashtag from(Hashtag hashtag) {
		return ThumbnailHashtag.builder().hashtag(hashtag).build();
	}

}