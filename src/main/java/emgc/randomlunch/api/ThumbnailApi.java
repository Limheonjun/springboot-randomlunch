package emgc.randomlunch.api;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import emgc.randomlunch.dto.ThumbnailResponse;
import emgc.randomlunch.service.function.ThumbnailService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("thumbnails")
public class ThumbnailApi {

	private ThumbnailService thumbnailService;

	//정렬이나 필터링의 경우에는 QueryString 사용
	@GetMapping(value = "", params = {"restaurantName", "page", "size"})
	public List<ThumbnailResponse> getThumbnailListByRestaurantName(
		@RequestParam String restaurantName,
		@RequestParam Pageable pageable
	) {
		return thumbnailService.getThumbnailListByRestaurantName(restaurantName, pageable);
	}

	@GetMapping(value = "", params = {"keyword", "page", "size"})
	public List<ThumbnailResponse> getThumbnailListByHashtag(
		@RequestParam String keyword,
		@RequestParam Pageable pageable
	) {
		return thumbnailService.getThumbnailListByHashtag(keyword, pageable);
	}

	@GetMapping(value = "", params = {"categoryId", "restaurantName", "page", "size"})
	public List<ThumbnailResponse> getThumbnailListByCategoryAndRestaurantName(
		@RequestParam Long categoryId,
		@RequestParam String restaurantName,
		@RequestParam Pageable pageable
	) {
		return thumbnailService.getThumbnailListByCategoryAndRestaurantName(categoryId, restaurantName, pageable);
	}

	@GetMapping(value = "", params = {"categoryId", "keyword", "page", "size"})
	public List<ThumbnailResponse> getThumbnailListByCategoryAndHashtag(
		@RequestParam Long categoryId,
		@RequestParam String keyword,
		@RequestParam Pageable pageable
	) {
		return thumbnailService.getThumbnailListByCategoryAndHashtag(categoryId, keyword, pageable);
	}

}
