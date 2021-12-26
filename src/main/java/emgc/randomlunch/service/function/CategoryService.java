package emgc.randomlunch.service.function;

import java.util.List;

import emgc.randomlunch.dto.category.CategoryResponse;

public interface CategoryService {

	List<CategoryResponse> getCategories();

}
