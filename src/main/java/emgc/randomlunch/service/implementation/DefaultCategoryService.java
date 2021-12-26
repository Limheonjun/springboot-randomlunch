package emgc.randomlunch.service.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import emgc.randomlunch.dto.category.CategoryResponse;
import emgc.randomlunch.entity.Category;
import emgc.randomlunch.repository.CategoryRepository;
import emgc.randomlunch.service.function.CategoryService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefaultCategoryService implements CategoryService {

	private final CategoryRepository categoryRepository;

	@Override
	public List<CategoryResponse> getCategories() {
		List<Category> all = categoryRepository.findAll();
		return all.stream().map(CategoryResponse::from).collect(Collectors.toList());
	}

}
