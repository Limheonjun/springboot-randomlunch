package emgc.randomlunch.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import emgc.randomlunch.entity.Category;
import emgc.randomlunch.entity.Restaurant;
import emgc.randomlunch.entity.Thumbnail;
import emgc.randomlunch.entity.ThumbnailHashtag;
import emgc.randomlunch.entity.User;

public interface ThumbnailRepository extends JpaRepository<Thumbnail, Long> {

	List<Thumbnail> findByRestaurant(Restaurant restaurant, Pageable pageable);

	List<Thumbnail> findByUser(User user, Pageable pageable);

	/**
	 * 전체 카테고리에서 음식점 명이 포함된 것을 기준으로 검색 최신순(기본옵션)
	 * RestaurantRepository.findAllByNameLike를 통해 음식점 먼저 검색
	 */
	List<Thumbnail> findAllByRestaurantInOrderByCreateDateDesc(
		List<Restaurant> restaurantList,
		Pageable pageable
	);

	/**
	 * 전체 카테고리에서 해시태그 명이 포함된 것을 기준으로 검색 최신순(기본옵션)
	 * HashtagRepository.findAllByWordLike를 통해 해시태그 검색
	 * ThumbnailHashtagRepository.findAllByHashtagIn을 통해 해시태그가 포함된 튜플 선택
	 */
	List<Thumbnail> findAllByThumbnailHashtagInAndRestaurantInOrderByCreateDateDesc(
		List<ThumbnailHashtag> thumbnailHashtagList,
		List<Restaurant> restaurantList,
		Pageable pageable
	);

	/**
	 * 특정 카테고리에서 음식점 명이 포함된 것을 기준으로 검색 최신순(기본옵션)
	 * RestaurantRepository.findAllByNameLike를 통해 음식점 먼저 검색
	 */
	List<Thumbnail> findAllByCategoryAndRestaurantInOrderByCreateDateDesc(
		Category category,
		List<Restaurant> restaurantList,
		Pageable pageable
	);

	/**
	 * 전체 카테고리에서 해시태그 명이 포함된 것을 기준으로 검색 최신순(기본옵션)
	 * HashtagRepository.findAllByWordLike를 통해 해시태그 검색
	 * ThumbnailHashtagRepository.findAllByHashtagIn을 통해 해시태그가 포함된 튜플 선택
	 */
	List<Thumbnail> findAllByCategoryAndThumbnailHashtagInAndRestaurantInOrderByCreateDateDesc(
		Category category,
		List<ThumbnailHashtag> thumbnailHashtagList,
		List<Restaurant> restaurantList,
		Pageable pageable
	);

}
