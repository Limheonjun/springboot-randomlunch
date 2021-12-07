package emgc.randomlunch.dto;

import emgc.randomlunch.entity.Thumbnail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ThumbnailResponse {

	private Long thumbnailId;

	private Long restaurantId;

	private String restaurantName;

	private Long categoryId;

	private String categoryName;

	private Long userId;

	private String nickName;

	private Long size;

	private Integer height;

	private Integer width;

	private Long likes;

	private Long views;

	private String originalFileName;

	private String fileName;

	private String extension;

	public static ThumbnailResponse from(Thumbnail thumbnail) {
		Long thumbnailId = thumbnail.getId();
		Long restaurantId = thumbnail.getRestaurant().getId();
		String restaurantName = thumbnail.getRestaurant().getName();
		Long categoryId = thumbnail.getCategory().getId();
		String categoryName = thumbnail.getCategory().getName();
		Long size = thumbnail.getSize();
		Integer height = thumbnail.getHeight();
		Integer width = thumbnail.getWidth();
		Long likes = thumbnail.getLikes();
		Long views = thumbnail.getViews();
		String originalFileName = thumbnail.getOriginalFileName();
		String fileName = thumbnail.getFileName();
		String extension = thumbnail.getExtension();

		return ThumbnailResponse.builder()
			.thumbnailId(thumbnailId)
			.restaurantId(restaurantId)
			.restaurantName(restaurantName)
			.categoryId(categoryId)
			.categoryName(categoryName)
			.size(size)
			.height(height)
			.width(width)
			.likes(likes)
			.views(views)
			.originalFileName(originalFileName)
			.fileName(fileName)
			.extension(extension)
			.build();
	}

}
