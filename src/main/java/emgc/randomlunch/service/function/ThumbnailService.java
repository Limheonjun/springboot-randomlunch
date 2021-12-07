package emgc.randomlunch.service.function;

import java.util.List;

import org.springframework.data.domain.Pageable;

import emgc.randomlunch.dto.ThumbnailResponse;
import emgc.randomlunch.entity.Category;
import emgc.randomlunch.entity.Restaurant;
import emgc.randomlunch.entity.Thumbnail;
import emgc.randomlunch.entity.ThumbnailHashtag;

public interface ThumbnailService {

	List<ThumbnailResponse> getThumbnailListByRestaurantName(
		String restaurentName,
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
