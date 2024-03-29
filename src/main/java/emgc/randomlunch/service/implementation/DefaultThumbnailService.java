package emgc.randomlunch.service.implementation;

import static emgc.randomlunch.enums.RestaurantStatus.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import emgc.randomlunch.dto.thumbnail.ThumbnailCreateRequest;
import emgc.randomlunch.dto.thumbnail.ThumbnailResponse;
import emgc.randomlunch.entity.Category;
import emgc.randomlunch.entity.Hashtag;
import emgc.randomlunch.entity.Restaurant;
import emgc.randomlunch.entity.Thumbnail;
import emgc.randomlunch.entity.ThumbnailHashtag;
import emgc.randomlunch.entity.User;
import emgc.randomlunch.repository.CategoryRepository;
import emgc.randomlunch.repository.HashtagRepository;
import emgc.randomlunch.repository.RestaurantRepository;
import emgc.randomlunch.repository.ThumbnailHashtagRepository;
import emgc.randomlunch.repository.ThumbnailRepository;
import emgc.randomlunch.repository.UserRepository;
import emgc.randomlunch.service.function.HashtagService;
import emgc.randomlunch.service.function.ThumbnailService;
import emgc.randomlunch.util.FileUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefaultThumbnailService implements ThumbnailService {

	private final ThumbnailRepository thumbnailRepository;

	private final RestaurantRepository restaurantRepository;

	private final HashtagRepository hashtagRepository;

	private final ThumbnailHashtagRepository thumbnailHashtagRepository;

	private final CategoryRepository categoryRepository;

	private final UserRepository userRepository;

	private final HashtagService hashtagService;

	@Override
	public List<ThumbnailResponse> getThumbnailListByRestaurantName(
		String restaurantName,
		BigDecimal latitude,
		BigDecimal longitude,
		Float distance,
		Pageable pageable
	) {
		List<Restaurant> restaurantList = restaurantRepository.findAllByNameAndGPSAndDistance(latitude,
			longitude, distance, 0, 100, OPEN.name(), restaurantName);
		List<Thumbnail> thumbnailList = thumbnailRepository.findAllByRestaurantInOrderByCreateDateDesc(
			restaurantList,
			pageable
		);

		return thumbnailList.stream().map(ThumbnailResponse::from).collect(Collectors.toList());
	}

	@Override
	public List<ThumbnailResponse> getThumbnailListByHashtag(
		String keyword,
		BigDecimal latitude,
		BigDecimal longitude,
		Float distance,
		Pageable pageable
	) {
		List<Hashtag> hashtagList = hashtagRepository.findAllByWordLike(keyword);
		List<ThumbnailHashtag> thumbnailHashtagList = thumbnailHashtagRepository.findAllByHashtagIn(hashtagList);
		//검색된 썸네일 중에서 반경 x미터 해당하는 것만 반환
		List<Restaurant> restaurantList = restaurantRepository.findAllByNameAndGPSAndDistance(latitude,
			longitude, distance, 0, 100, OPEN.name(), "");
		List<Thumbnail> thumbnailList = thumbnailRepository.findAllByThumbnailHashtagInAndRestaurantInOrderByCreateDateDesc(
			thumbnailHashtagList,
			restaurantList,
			pageable
		);

		return thumbnailList.stream().map(ThumbnailResponse::from).collect(Collectors.toList());
	}

	@Override
	public List<ThumbnailResponse> getThumbnailListByCategoryAndRestaurantName(
		Long categoryId,
		String restaurantName,
		BigDecimal latitude,
		BigDecimal longitude,
		Float distance,
		Pageable pageable
	) {
		Category category = categoryRepository.findById(categoryId).orElseThrow();//TODO : 구체적 예외 처리
		List<Restaurant> restaurantList = restaurantRepository.findAllByNameAndGPSAndDistance(latitude,
			longitude, distance, 0, 100, OPEN.name(), restaurantName);
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
		BigDecimal latitude,
		BigDecimal longitude,
		Float distance,
		Pageable pageable
	) {
		List<Hashtag> hashtagList = hashtagRepository.findAllByWordLike(keyword);
		List<ThumbnailHashtag> thumbnailHashtagList = thumbnailHashtagRepository.findAllByHashtagIn(hashtagList);
		Category category = categoryRepository.findById(categoryId).orElseThrow();//TODO : 구체적 예외 처리
		//검색된 썸네일 중에서 반경 x미터 해당하는 것만 반환
		List<Restaurant> restaurantList = restaurantRepository.findAllByNameAndGPSAndDistance(latitude,
			longitude, distance, 0, 100, OPEN.name(), "");
		List<Thumbnail> thumbnailList = thumbnailRepository.findAllByCategoryAndThumbnailHashtagInAndRestaurantInOrderByCreateDateDesc(
			category,
			thumbnailHashtagList,
			restaurantList,
			pageable
		);

		return thumbnailList.stream().map(ThumbnailResponse::from).collect(Collectors.toList());
	}

	@Override
	public ThumbnailResponse createThumbnailWithHashtag(
		MultipartFile file,
		String email,
		ThumbnailCreateRequest request
	) throws IOException {
		Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
			.orElseThrow();//TODO : 구체적인 예외 처리
		Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow();
		User user = userRepository.findByEmail(email).orElseThrow();
		//해시태그가 있으면 가져오고 없으면 생성하고 저장
		List<Hashtag> hashtagList = hashtagService.createHashtagList(request.getHashtagList());

		//썸네일 생성
		Thumbnail thumbnail = Thumbnail.of(file, restaurant, category, user);
		Thumbnail savedThumbnail = thumbnailRepository.save(thumbnail);

		//썸네일해시태그 생성
		List<ThumbnailHashtag> thumbnailHashtagList = hashtagList.stream()
			.map(hashtag -> ThumbnailHashtag.builder().hashtag(hashtag).thumbnail(savedThumbnail).build())
			.collect(Collectors.toList());
		thumbnailHashtagRepository.saveAll(thumbnailHashtagList);

		return ThumbnailResponse.from(thumbnail);
	}

	@Override
	@Transactional
	public void uploadThumbnails(
		List<MultipartFile> thumbnails,
		ThumbnailCreateRequest request,
		String email
	) throws IOException {
		User user = userRepository.findByEmail(email).orElseThrow();
		Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId()).orElseThrow();
		Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow();
		List<Hashtag> hashtagList = hashtagService.createHashtagList(request.getHashtagList());
		String path = "C:\\Users\\MS\\Desktop\\thumbnail";
		for (MultipartFile file : thumbnails) {
			Thumbnail thumbnail = Thumbnail.of(file, restaurant, category, user);
			Thumbnail savedThumbnail = thumbnailRepository.save(thumbnail);
			awsS3UploadUtil.upload(file, "static");
			// FileUtil.saveFile(file, path);

			//썸네일해시태그 생성
			List<ThumbnailHashtag> thumbnailHashtagList = hashtagList.stream()
				.map(hashtag -> ThumbnailHashtag.builder().hashtag(hashtag).thumbnail(savedThumbnail).build())
				.collect(Collectors.toList());
			thumbnailHashtagRepository.saveAll(thumbnailHashtagList);
		}
	}
}
