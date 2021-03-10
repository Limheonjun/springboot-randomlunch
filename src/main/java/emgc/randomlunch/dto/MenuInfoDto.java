package emgc.randomlunch.dto;

import emgc.randomlunch.entity.Menu;
import emgc.randomlunch.entity.Restaurant;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuInfoDto {

    private Long id;
    private Long restaurantId;
    private String name;
    private int price;

    public MenuInfoDto(Menu menu) {
        this.id = menu.getId();
        this.restaurantId = menu.getRestaurant().getId();
        this.name = menu.getName();
        this.price = menu.getPrice();
    }

    public Menu makeMenu(Restaurant restaurant) {
        Menu menu = Menu.builder()
                .restaurant(restaurant)
                .name(name)
                .price(price)
                .build();
        return menu;
    }
}
