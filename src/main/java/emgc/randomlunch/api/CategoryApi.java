package emgc.randomlunch.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import emgc.randomlunch.dto.category.CategoryResponse;
import emgc.randomlunch.service.function.CategoryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("categories")
public class CategoryApi {

	private final CategoryService categoryService;

	@GetMapping("")
	public List<CategoryResponse> getCategories() {
		return categoryService.getCategories();
	}

}
