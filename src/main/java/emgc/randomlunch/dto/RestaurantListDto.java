package emgc.randomlunch.dto;

import emgc.randomlunch.entity.Restaurant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantListDto {

    private Long restaurantId;
    private String restaurantName;
    private String restaurantAddress;

    public RestaurantListDto(Restaurant restaurant) {
        restaurantId = restaurant.getId();
        restaurantName = restaurant.getName();
        restaurantAddress = restaurant.getAddress();
    }

}
