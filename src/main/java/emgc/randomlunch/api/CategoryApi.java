package emgc.randomlunch.api;

import emgc.randomlunch.dto.CategoryInfoDto;
import emgc.randomlunch.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
@Slf4j
public class CategoryApi {

    private final CategoryService categoryService;

    @PostMapping("/upload")
    public void addCategory(@RequestBody CategoryInfoDto categoryInfoDto, Principal principal) {
        log.info("Received a request to create a category {} from user {}", categoryInfoDto.getName(), principal.getName());
        categoryService.addCategory(categoryInfoDto);
    }

    @GetMapping("/list")
    public List<CategoryInfoDto> getCategoryList() {
        log.info("Retrieving categories");
        return categoryService.getCategoryList();
    }

    @PostMapping("/edit")
    public void editCategoryInfo(@RequestBody CategoryInfoDto categoryInfoDto, Principal principal) {
        log.info("Received a edit request for category id {} from user {}", categoryInfoDto.getId(), principal.getName());
        categoryService.editCategory(categoryInfoDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCategory(@PathVariable Long id, Principal principal) {
        log.info("Received a delete request for category id {} from user {}", id, principal.getName());
        categoryService.deleteCategory(id);
    }

}
