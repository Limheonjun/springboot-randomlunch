package emgc.randomlunch.dto;

import emgc.randomlunch.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CategoryInfoDto {

    private Long categoryId;
    private String name;

    public CategoryInfoDto(Category category) {
        this.categoryId = category.getId();
        this.name = category.getName();
    }
}
