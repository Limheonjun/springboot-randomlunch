package emgc.randomlunch.dto;

import emgc.randomlunch.entity.Menu;
import emgc.randomlunch.entity.Restaurant;
import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuInfoDto {

    private Long id;

    @NotNull(message = "음식점 id는 필수값 입니다.")
    @Positive(message = "음식점 id는 양의 정수이어야 합니다.")
    @Min(value = 1L, message = "음식점의 아이디는 최소 1 이상이어야 합니다.")
    private Long restaurantId;

    @NotBlank(message = "메뉴 이름은 필수값 입니다.")
    @Size(min = 1, max = 20, message = "메뉴 이름은 1~20자 이어야 합니다.")
    private String name;

    @Positive(message = "메뉴의 가격은 양의 정수이어야 합니다.")
    @Min(value = 1, message = "메뉴의 가격은 최소 1원 이상이어야 합니다.")
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
