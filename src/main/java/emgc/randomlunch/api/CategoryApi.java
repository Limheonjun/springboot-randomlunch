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

    private final CategoryRepository categoryRepository;
    private final CategoryService service;

    @PostMapping("/upload")
    public void uploadCategory(@RequestBody CategoryInfoDto categoryInfoDto) throws Exception {
        service.addCategory(categoryInfoDto);
    }

    @GetMapping("/list")
    public List<CategoryInfoDto> getCategoryList() {
        return service.getCategoryList();
    }

    @PostMapping("/edit")
    public void editCategoryInfo(@RequestBody CategoryInfoDto categoryInfoDto) throws Exception {
        service.editCategory(categoryInfoDto);
    }

    @DeleteMapping("/delete")
    public void deleteCategory(@RequestParam Long categoryId) throws Exception {
        service.deleteCategory(categoryId);
    }

}
