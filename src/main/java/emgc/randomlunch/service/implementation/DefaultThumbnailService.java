package emgc.randomlunch.service.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import emgc.randomlunch.dto.ThumbnailResponse;
import emgc.randomlunch.entity.Category;
import emgc.randomlunch.entity.Hashtag;
import emgc.randomlunch.entity.Restaurant;
import emgc.randomlunch.entity.Thumbnail;
import emgc.randomlunch.entity.ThumbnailHashtag;
import emgc.randomlunch.repository.CategoryRepository;
import emgc.randomlunch.repository.HashtagRepository;
import emgc.randomlunch.repository.RestaurantRepository;
import emgc.randomlunch.repository.ThumbnailHashtagRepository;
import emgc.randomlunch.repository.ThumbnailRepository;
import emgc.randomlunch.service.function.ThumbnailService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefaultThumbnailService implements ThumbnailService {

	private final ThumbnailRepository thumbnailRepository;

	private final RestaurantRepository restaurantRepository;

	private final HashtagRepository hashtagRepository;

	private final ThumbnailHashtagRepository thumbnailHashtagRepository;

	private final CategoryRepository categoryRepository;

	@Override
	public List<ThumbnailResponse> getThumbnailListByRestaurantName(
		String restaurantName,
		Pageable pageable
	) {
		List<Restaurant> restaurantList = restaurantRepository.findAllByNameLike(restaurantName);
		List<Thumbnail> thumbnailList = thumbnailRepository.findAllByRestaurantInOrderByCreateDateDesc(
			restaurantList,
			pageable
		);

		return thumbnailList.stream().map(ThumbnailResponse::from).collect(Collectors.toList());
	}

	@Override
	public List<ThumbnailResponse> getThumbnailListByHashtag(
		String keyword,
		Pageable pageable
	) {
		List<Hashtag> hashtagList = hashtagRepository.findAllByWordLike(keyword);
		List<ThumbnailHashtag> thumbnailHashtagList = thumbnailHashtagRepository.findAllByHashtagIn(hashtagList);
		List<Thumbnail> thumbnailList = thumbnailRepository.findAllByThumbnailHashtagInOrderByCreateDateDesc(
			thumbnailHashtagList,
			pageable
		);

		return thumbnailList.stream().map(ThumbnailResponse::from).collect(Collectors.toList());
	}

	@Override
	public List<ThumbnailResponse> getThumbnailListByCategoryAndRestaurantName(
		Long categoryId,
		String restaurantName,
		Pageable pageable
	) {
		Category category = categoryRepository.findById(categoryId).orElseThrow();//TODO : 구체적 예외 처리
		List<Restaurant> restaurantList = restaurantRepository.findAllByNameLike(restaurantName);
		List<Thumbnail> thumbnailList = thumbnailRepository.findAllByCategoryAndRestaurantInOrderByCreateDateDesc(
			category,
			restaurantList,
			pageable
		);

		return thumbnailList.stream().map(ThumbnailResponse::from).collect(Collectors.toList());
	}

	@Override
	public List<ThumbnailResponse> getThumbnailListByCategoryAndHashtag(
		Long categoryId,
		String keyword,
		Pageable pageable
	) {
		List<Hashtag> hashtagList = hashtagRepository.findAllByWordLike(keyword);
		List<ThumbnailHashtag> thumbnailHashtagList = thumbnailHashtagRepository.findAllByHashtagIn(hashtagList);
		Category category = categoryRepository.findById(categoryId).orElseThrow();//TODO : 구체적 예외 처리
		List<Thumbnail> thumbnailList = thumbnailRepository.findAllByCategoryAndThumbnailHashtagInOrderByCreateDateDesc(
			category,
			thumbnailHashtagList,
			pageable
		);

		return thumbnailList.stream().map(ThumbnailResponse::from).collect(Collectors.toList());
	}
}
