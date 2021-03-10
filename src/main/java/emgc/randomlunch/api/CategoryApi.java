package emgc.randomlunch.api;

import emgc.randomlunch.dto.CategoryInfoDto;
import emgc.randomlunch.repository.CategoryRepository;
import emgc.randomlunch.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryApi {

    private final CategoryService categoryService;

    @PostMapping("/upload")
    public void uploadCategory(@RequestBody CategoryInfoDto categoryInfoDto) throws Exception {
        categoryService.addCategory(categoryInfoDto);
    }

    @GetMapping("/list")
    public List<CategoryInfoDto> getCategoryList() {
        return categoryService.getCategoryList();
    }

    @PostMapping("/edit")
    public void editCategoryInfo(@RequestBody CategoryInfoDto categoryInfoDto) throws Exception {
        categoryService.editCategory(categoryInfoDto);
    }

    @DeleteMapping("/delete")
    public void deleteCategory(@RequestParam Long categoryId) throws Exception {
        categoryService.deleteCategory(categoryId);
    }

}
