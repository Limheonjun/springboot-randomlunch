package emgc.randomlunch.service.implementation;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import emgc.randomlunch.entity.Category;
import emgc.randomlunch.entity.Restaurant;
import emgc.randomlunch.entity.Thumbnail;
import emgc.randomlunch.entity.ThumbnailHashtag;
import emgc.randomlunch.repository.ThumbnailRepository;
import emgc.randomlunch.service.function.ThumbnailService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefaultThumbnailService implements ThumbnailService {

	private final ThumbnailRepository thumbnailRepository;

	@Override
	public List<Thumbnail> getThumbnailListByRestaurantList(
		List<Restaurant> restaurantList,
		Pageable pageable
	) {
		return thumbnailRepository.findAllByRestaurantInOrderByCreateDateDesc(
			restaurantList,
			pageable
		);
	}

	@Override
	public List<Thumbnail> getThumbnailListByThumbnailHashtagList(
		List<ThumbnailHashtag> thumbnailHashtagList,
		Pageable pageable
	) {
		return thumbnailRepository.findAllByThumbnailHashtagInOrderByCreateDateDesc(
			thumbnailHashtagList,
			pageable
		);
	}

	@Override
	public List<Thumbnail> getThumbnailListByCategoryAndRestaurantList(
		Category category,
		List<Restaurant> restaurantList,
		Pageable pageable
	) {
		return thumbnailRepository.findAllByCategoryAndRestaurantInOrderByCreateDateDesc(
			category,
			restaurantList,
			pageable
		);
	}

	@Override
	public List<Thumbnail> getThumbnailListByCategoryAndThumbnailHashtagList(
		Category category,
		List<ThumbnailHashtag> thumbnailHashtagList,
		Pageable pageable
	) {
		return thumbnailRepository.findAllByCategoryAndThumbnailHashtagInOrderByCreateDateDesc(
			category,
			thumbnailHashtagList,
			pageable
		);
	}
}
