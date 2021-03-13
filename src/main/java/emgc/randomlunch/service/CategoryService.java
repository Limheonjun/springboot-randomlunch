package emgc.randomlunch.service;

import emgc.randomlunch.dto.CategoryInfoDto;
import emgc.randomlunch.entity.Category;
import emgc.randomlunch.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    //카테고리 목록 조회
    public List<CategoryInfoDto> getCategoryList() {
        List<CategoryInfoDto> categoryList = new ArrayList<>();
        List<Category> findCategoryList = repository.findAll();
        categoryList = findCategoryList.stream()
                        .map(CategoryInfoDto::new)
                        .collect(Collectors.toList());
        return categoryList;
    }

    //카테고리 추가
    public void addCategory(CategoryInfoDto categoryInfoDto) throws Exception{
        //Optional<Category> optional = repository.findById(categoryInfoDto.getId());
        //if(!optional.isEmpty()) throw new Exception();
        Category category = new Category(categoryInfoDto);
        repository.save(category);
    }

    //카테고리 수정
    public void editCategory(CategoryInfoDto categoryInfoDto) throws Exception {
        Category category = repository.findById(categoryInfoDto.getId()).orElseThrow();
        category.setName(categoryInfoDto.getName());
        repository.save(category);
    }

    //카테고리 삭제
    public void deleteCategory(Long id) throws Exception {
        Category category = repository.findById(id).orElseThrow();
        repository.delete(category);
    }

    //카테고리 조회
    public Category getCategory(Long id) {
        Category category = repository.findById(id).orElseThrow();
        return category;
    }
}
