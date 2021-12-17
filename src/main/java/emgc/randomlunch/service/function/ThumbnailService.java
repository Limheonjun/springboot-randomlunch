package emgc.randomlunch.service.function;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import emgc.randomlunch.dto.thumbnail.ThumbnailCreateRequest;
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

	ThumbnailResponse createThumbnailWithHashtag(
		MultipartFile file,
		String email,
		ThumbnailCreateRequest request
	) throws IOException;

	void uploadThumbnails(
		List<MultipartFile> thumbnails,
		ThumbnailCreateRequest request,
		String email
	) throws IOException;

}
