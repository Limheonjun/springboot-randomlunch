package emgc.randomlunch.dto.category;

import emgc.randomlunch.entity.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryResponse {

	private Long categoryId;

	private String categoryName;

	public static CategoryResponse from(Category category) {
		Long categoryId = category.getId();
		String categoryName = category.getType().getValue();

		return CategoryResponse.builder().categoryId(categoryId).categoryName(categoryName).build();
	}

}
