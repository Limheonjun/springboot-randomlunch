package emgc.randomlunch.dto;

import emgc.randomlunch.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
public class CategoryInfoDto {

    private Long id;

    @Size(min = 1, max = 10, message = "카테고리 이름은 1~5자 이어야 합니다.")
    @NotBlank(message = "카테고리 이름은 필수값 입니다.")
    private String name;

    public CategoryInfoDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }
}
