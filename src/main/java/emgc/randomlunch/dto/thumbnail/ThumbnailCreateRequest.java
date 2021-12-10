package emgc.randomlunch.dto.thumbnail;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class ThumbnailCreateRequest {

	private Long restaurantId;

	private Long categoryId;

	private List<String> hashtagList = new ArrayList<>();

}

