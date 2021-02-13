package emgc.randomlunch.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RestaurantInfoDto {

    private Long restaurantId;
    private List<String> menuList = new ArrayList<>();
    private List<Integer> priceList = new ArrayList<>();
    private String name;
    private String locationX;
    private String locationY;
    private String address;

}
