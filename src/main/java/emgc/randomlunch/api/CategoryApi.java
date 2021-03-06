package emgc.randomlunch.api;

import emgc.randomlunch.dto.CategoryInfoDto;
import emgc.randomlunch.dto.MenuInfoDto;
import emgc.randomlunch.entity.Category;
import emgc.randomlunch.entity.Menu;
import emgc.randomlunch.entity.Restaurant;
import emgc.randomlunch.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryApi {

    private final CategoryRepository categoryRepository;

    @PostMapping("/upload")
    public String uploadCategory(@RequestBody CategoryInfoDto categoryInfoDto) throws Exception {
        System.out.println("name : " + categoryInfoDto.getName());
        Category findCategory = categoryRepository.findByName(categoryInfoDto.getName());
        if(findCategory == null) {
            Category c = Category.builder().name(categoryInfoDto.getName()).build();
            categoryRepository.save(c);
            return "저장되었습니다";
        }
        throw new Exception("이미 존재하는 아이디입니다");
    }

    @GetMapping("/list")
    public List<CategoryInfoDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(CategoryInfoDto::new).collect(Collectors.toList());
    }

    @PostMapping("/edit")
    public void editCategory(@RequestBody CategoryInfoDto categoryInfoDto) {
        Category category = categoryRepository.findById(categoryInfoDto.getCategoryId()).get();
        category.setName(categoryInfoDto.getName());
        categoryRepository.save(category);
    }

    @DeleteMapping("/delete")
    public void deleteCategory(@RequestParam Long categoryId) {
        Category category = categoryRepository.findById(categoryId).get();
        categoryRepository.delete(category);
    }

}
