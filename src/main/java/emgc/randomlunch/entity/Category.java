package emgc.randomlunch.entity;

import emgc.randomlunch.dto.CategoryInfoDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Restaurant> restaurant = new ArrayList<>();

    public Category(CategoryInfoDto categoryInfoDto) {
        this.name = categoryInfoDto.getName();
    }
}
