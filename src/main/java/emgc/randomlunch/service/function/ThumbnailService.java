package emgc.randomlunch.service.function;

import java.util.List;

import org.springframework.data.domain.Pageable;

import emgc.randomlunch.dto.thumbnail.ThumbnailResponse;

public interface ThumbnailService {

	List<ThumbnailResponse> getThumbnailListByRestaurantName(
		String restaurantName,
		Pageable pageable
	);

	List<ThumbnailResponse> getThumbnailListByHashtag(
		String keyword,
		Pageable pageable
	);

	List<ThumbnailResponse> getThumbnailListByCategoryAndRestaurantName(
		Long categoryId,
		String restaurantName,
		Pageable pageable
	);

	List<ThumbnailResponse> getThumbnailListByCategoryAndHashtag(
		Long categoryId,
		String keyword,
		Pageable pageable
	);

}
