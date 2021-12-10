package emgc.randomlunch.api;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import emgc.randomlunch.dto.thumbnail.ThumbnailResponse;
import emgc.randomlunch.service.function.ThumbnailService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("thumbnails")
public class ThumbnailApi {

	private ThumbnailService thumbnailService;

	@GetMapping(value = "", params = {"restaurantName", "page", "size"})
	public List<ThumbnailResponse> getThumbnailListByRestaurantName(
		@RequestParam @NotNull(message = "음식점명은 필수 입력 값입니다") String restaurantName,
		@RequestParam Pageable pageable
	) {
		return thumbnailService.getThumbnailListByRestaurantName(restaurantName, pageable);
	}

	@GetMapping(value = "", params = {"keyword", "page", "size"})
	public List<ThumbnailResponse> getThumbnailListByHashtag(
		@RequestParam @NotNull(message = "해시태그는 필수 입력 값입니다") String keyword,
		@RequestParam Pageable pageable
	) {
		return thumbnailService.getThumbnailListByHashtag(keyword, pageable);
	}

	@GetMapping(value = "", params = {"categoryId", "restaurantName", "page", "size"})
	public List<ThumbnailResponse> getThumbnailListByCategoryAndRestaurantName(
		@RequestParam
		@NotNull(message = "카테고리 아이디는 필수 입력 값입니다")
		@Positive(message = "카테고리 아이디는 양수만 입력 가능합니다.") Long categoryId,
		@RequestParam
		@NotNull(message = "음식점명은 필수 입력 값입니다") String restaurantName,
		@RequestParam Pageable pageable
	) {
		return thumbnailService.getThumbnailListByCategoryAndRestaurantName(categoryId, restaurantName, pageable);
	}

	@GetMapping(value = "", params = {"categoryId", "keyword", "page", "size"})
	public List<ThumbnailResponse> getThumbnailListByCategoryAndHashtag(
		@RequestParam
		@NotNull(message = "카테고리 아이디는 필수 입력 값입니다")
		@Positive(message = "카테고리 아이디는 양수만 입력 가능합니다.") Long categoryId,
		@RequestParam
		@NotNull(message = "해시태그는 필수 입력 값입니다") String keyword,
		@RequestParam Pageable pageable
	) {
		return thumbnailService.getThumbnailListByCategoryAndHashtag(categoryId, keyword, pageable);
	}

}
