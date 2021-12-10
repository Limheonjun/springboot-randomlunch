package emgc.randomlunch.entity;

import java.awt.image.BufferedImage;
import java.io.IOException;
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

import org.springframework.web.multipart.MultipartFile;

import emgc.randomlunch.util.FileUtil;
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

	@OneToMany(mappedBy = "thumbnail", cascade = CascadeType.PERSIST)
	private List<ThumbnailHashtag> thumbnailHashtagList = new ArrayList<>();

	private Long size;

	private Integer height;

	private Integer width;

	private String originalFileName;

	private String fileName;

	private String extension;

	private Integer likes;

	private Integer views;

	public static Thumbnail of(
		MultipartFile file,
		Restaurant restaurant,
		Category category,
		User user
	) throws IOException {
		BufferedImage bufferedImage = FileUtil.toBufferedImage(file);
		long size = file.getSize();
		int height = FileUtil.getHeight(bufferedImage);
		int width = FileUtil.getWidth(bufferedImage);
		String extension = FileUtil.getExtension(file);
		String originalName = FileUtil.getOriginalName(file);
		String originFileName = FileUtil.getOriginFileName(file);

		return Thumbnail.builder()
			.restaurant(restaurant)
			.category(category)
			.user(user)
			.size(size)
			.width(width)
			.height(height)
			.extension(extension)
			.originalFileName(originalName)
			.originalFileName(originFileName)
			.views(0)
			.likes(0)
			.build();
	}

}
