package emgc.randomlunch.service;

import emgc.randomlunch.dto.CategoryInfoDto;
import emgc.randomlunch.entity.Category;
import emgc.randomlunch.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    public List<CategoryInfoDto> getCategoryList() {
        List<Category> findCategoryList = repository.findAll();
        return findCategoryList.stream().map(CategoryInfoDto::new).collect(Collectors.toList());
    }

    public Category getCategory(CategoryInfoDto categoryInfoDto) {
        return getCategory(categoryInfoDto.getId());
    }

    public Category getCategory(Long categoryId) {
        return repository.findById(categoryId).orElseThrow();
    }

    public Category addCategory(CategoryInfoDto categoryInfoDto) {
        Category category = new Category(categoryInfoDto);
        return repository.save(category);
    }

    public Category editCategory(CategoryInfoDto categoryInfoDto) {
        Category category = repository.findById(categoryInfoDto.getId()).orElseThrow();
        category.setName(categoryInfoDto.getName());
        return repository.save(category);
    }

    public void deleteCategory(Long id) {
        Category category = repository.findById(id).orElseThrow();
        repository.delete(category);
    }


}
