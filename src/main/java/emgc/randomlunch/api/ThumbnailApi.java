package emgc.randomlunch.api;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import emgc.randomlunch.dto.thumbnail.ThumbnailCreateRequest;
import emgc.randomlunch.dto.thumbnail.ThumbnailResponse;
import emgc.randomlunch.service.function.ThumbnailService;
import lombok.RequiredArgsConstructor;

/**
 * 썸네일 API
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("thumbnails")
public class ThumbnailApi {

	private final ThumbnailService thumbnailService;

	/**
	 * 내 위치 기준 반경 x미터 안에 있는 음식점 중 음식점 이름이 들어간 음식점목록을 페이지형태로 반환합니다.
	 *
	 * @param restaurantName 음식점 이름
	 * @param latitude 위도
	 * @param longitude 경도
	 * @param distance 반경
	 * @param pageable 페이지
	 * @return List<ThumbnailResponse> 썸네일 목록
	 */
	@GetMapping(value = "", params = {"restaurantName", "latitude", "longitude", "distance", "page", "size"})
	public List<ThumbnailResponse> getThumbnailListByRestaurantName(
		@RequestParam @NotNull(message = "음식점명은 필수 입력 값입니다") String restaurantName,
		@RequestParam BigDecimal latitude,
		@RequestParam BigDecimal longitude,
		@RequestParam Float distance,
		Pageable pageable
	) {
		return thumbnailService.getThumbnailListByRestaurantName(restaurantName, latitude, longitude, distance,
			pageable);
	}

	/**
	 * 내 위치 기준 반경 x미터 안에 있는 음식점 중 해시태그가 들어간 음식점목록을 페이지형태로 반환합니다.
	 *
	 * @param keyword 해시태그
	 * @param latitude 위도
	 * @param longitude 경도
	 * @param distance 반경
	 * @param pageable 페이지
	 * @return List<ThumbnailResponse> 썸네일 목록
	 */
	@GetMapping(value = "", params = {"keyword", "latitude", "longitude", "distance", "page", "size"})
	public List<ThumbnailResponse> getThumbnailListByHashtag(
		@RequestParam @NotNull(message = "해시태그는 필수 입력 값입니다") String keyword,
		@RequestParam BigDecimal latitude,
		@RequestParam BigDecimal longitude,
		@RequestParam Float distance,
		Pageable pageable
	) {
		return thumbnailService.getThumbnailListByHashtag(keyword, latitude, longitude, distance, pageable);
	}

	/**
	 * 내 위치 기준 반경 x미터 안에 있는 음식점 중 음식점 이름이 들어가고 카테고리에 속해있는 음식점목록을 페이지형태로 반환합니다.
	 * (추후 음식점명은 음식점 명 자동완성을 통해 음식점 ID로 교체될 예정)
	 *
	 * @param categoryId 카테고리 아이디
	 * @param restaurantName 음식점 이름
	 * @param latitude 위도
	 * @param longitude 경도
	 * @param distance 반경
	 * @param pageable 페이지
	 * @return List<ThumbnailResponse> 썸네일 목록
	 */
	@GetMapping(value = "", params = {"categoryId", "restaurantName", "latitude", "longitude", "distance", "page",
		"size"})
	public List<ThumbnailResponse> getThumbnailListByCategoryAndRestaurantName(
		@RequestParam
		@NotNull(message = "카테고리 아이디는 필수 입력 값입니다")
		@Positive(message = "카테고리 아이디는 양수만 입력 가능합니다.") Long categoryId,
		@RequestParam
		@NotNull(message = "음식점명은 필수 입력 값입니다") String restaurantName,
		@RequestParam BigDecimal latitude,
		@RequestParam BigDecimal longitude,
		@RequestParam Float distance,
		Pageable pageable
	) {
		return thumbnailService.getThumbnailListByCategoryAndRestaurantName(categoryId, restaurantName, latitude,
			longitude, distance, pageable);
	}

	/**
	 * 내 위치 기준 반경 x미터 안에 있는 음식점 중 해시태그가 들어가고 카테고리에 속해있는 음식점목록을 페이지형태로 반환합니다.
	 *
	 * @param categoryId 카테고리 아이디
	 * @param keyword 해시태그
	 * @param latitude 위도
	 * @param longitude 경도
	 * @param distance 반경
	 * @param pageable 페이지
	 * @return List<ThumbnailResponse> 썸네일 목록
	 */
	@GetMapping(value = "", params = {"categoryId", "keyword", "latitude", "longitude", "distance", "page", "size"})
	public List<ThumbnailResponse> getThumbnailListByCategoryAndHashtag(
		@RequestParam
		@NotNull(message = "카테고리 아이디는 필수 입력 값입니다")
		@Positive(message = "카테고리 아이디는 양수만 입력 가능합니다.") Long categoryId,
		@RequestParam
		@NotNull(message = "해시태그는 필수 입력 값입니다") String keyword,
		@RequestParam BigDecimal latitude,
		@RequestParam BigDecimal longitude,
		@RequestParam Float distance,
		Pageable pageable
	) {
		return thumbnailService.getThumbnailListByCategoryAndHashtag(categoryId, keyword, latitude, longitude, distance,
			pageable);
	}

	/**
	 * 썸네일 이미지와 썸네일 관련 정보를 사용자로부터 받고, 인증 주체를 받아와 사용자의 썸네일을 저장합니다.
	 *
	 * @param thumbnails 썸네일 이미지
	 * @param request 썸네일 관련 정보
	 * @param principal 인증 주체
	 * @throws IOException 파일 읽기/쓰기 예외
	 */
	@PostMapping("")
	public void uploadThumbnails(
		@RequestPart("thumbnails") List<MultipartFile> thumbnails,
		@RequestPart("request") ThumbnailCreateRequest request,
		Principal principal
	) throws IOException {
		String email = principal.getName();
		thumbnailService.uploadThumbnails(thumbnails, request, email);
	}

}
