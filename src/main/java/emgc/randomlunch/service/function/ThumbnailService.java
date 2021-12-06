package emgc.randomlunch.service.function;

import java.util.List;

import org.springframework.data.domain.Pageable;

import emgc.randomlunch.entity.Category;
import emgc.randomlunch.entity.Restaurant;
import emgc.randomlunch.entity.Thumbnail;
import emgc.randomlunch.entity.ThumbnailHashtag;

public interface ThumbnailService {

	List<Thumbnail> getThumbnailListByRestaurantList(
		List<Restaurant> restaurantList,
		Pageable pageable
	);

	List<Thumbnail> getThumbnailListByThumbnailHashtagList(
		List<ThumbnailHashtag> thumbnailHashtagList,
		Pageable pageable
	);

	List<Thumbnail> getThumbnailListByCategoryAndRestaurantList(
		Category category,
		List<Restaurant> restaurantList,
		Pageable pageable
	);

	List<Thumbnail> getThumbnailListByCategoryAndThumbnailHashtagList(
		Category category,
		List<ThumbnailHashtag> thumbnailHashtagList,
		Pageable pageable
	);

}
