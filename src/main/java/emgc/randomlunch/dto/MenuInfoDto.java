package emgc.randomlunch.dto;

import emgc.randomlunch.entity.Menu;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuInfoDto {

    private Long menuId;
    private Long restaurantId;
    private String name;
    private int price;

    public MenuInfoDto(Menu menu) {
        this.menuId = menu.getId();
        this.restaurantId = menu.getRestaurant().getId();
        this.name = menu.getName();
        this.price = menu.getPrice();
    }
}
