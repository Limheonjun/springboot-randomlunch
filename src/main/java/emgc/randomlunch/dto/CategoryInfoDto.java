package emgc.randomlunch.dto;

import emgc.randomlunch.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CategoryInfoDto {

    private Long id;
    private String name;

    public CategoryInfoDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();

    }
}
