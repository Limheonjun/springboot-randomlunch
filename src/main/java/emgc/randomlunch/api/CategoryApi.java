package emgc.randomlunch.api;

import emgc.randomlunch.dto.CategoryInfoDto;
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
    public void addCategory(@RequestBody CategoryInfoDto categoryInfoDto) {
        categoryService.addCategory(categoryInfoDto);
    }

    @GetMapping("/list")
    public List<CategoryInfoDto> getCategoryList() {
        return categoryService.getCategoryList();
    }

    @PostMapping("/edit")
    public void editCategoryInfo(@RequestBody CategoryInfoDto categoryInfoDto) {
        categoryService.editCategory(categoryInfoDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }

}
