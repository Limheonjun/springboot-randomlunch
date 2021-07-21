package emgc.randomlunch.api;

import emgc.randomlunch.dto.CategoryInfoDto;
import emgc.randomlunch.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
@Slf4j
@Validated
public class CategoryApi {

    private final CategoryService categoryService;

    @PostMapping("/upload")
    public void addCategory(
            @RequestBody @Valid CategoryInfoDto categoryInfoDto,
            @NotNull(message = "카테고리 추가는 로그인이 필요합니다.") Principal principal,
            BindingResult bindingResult
    ) {
        log.info("Received a request to create a category {} from user {}", categoryInfoDto.getName(), principal.getName());
        categoryService.addCategory(categoryInfoDto);
    }

    @GetMapping("/list")
    public List<CategoryInfoDto> getCategoryList() {
        log.info("Retrieving categories");
        return categoryService.getCategoryList();
    }

    @PostMapping("/edit")
    public void editCategoryInfo(
            @RequestBody @Valid CategoryInfoDto categoryInfoDto,
            @NotNull(message = "카테고리 수정은 로그인이 필요합니다.") Principal principal
    ) {
        log.info("Received a edit request for category id {} from user {}", categoryInfoDto.getId(), principal.getName());
        categoryService.editCategory(categoryInfoDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCategory(
            @PathVariable
            @NotNull(message = "삭제할 카테고리 아이디는 필수값 입니다.")
            @Positive(message = "삭제할 카테고리 아이디는 양의 정수이어야 합니다.")
            @Min(value = 1L, message = "삭제할 카테고리 아이디는 최소 1 이상이어야 합니다.") Long id,
            @NotNull(message = "카테고리 삭제는 로그인이 필요합니다.") Principal principal
    ) {
        log.info("Received a delete request for category id {} from user {}", id, principal.getName());
        categoryService.deleteCategory(id);
    }

}
