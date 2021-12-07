package emgc.randomlunch.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
public class Hashtag extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "hashtag_id")
	private Long id;

	private String word;

	@OneToMany(mappedBy = "hashtag", cascade = CascadeType.ALL)
	private List<ThumbnailHashtag> thumbnailHashtagList = new ArrayList<>();

}