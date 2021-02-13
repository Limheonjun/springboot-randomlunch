package emgc.randomlunch.dto;

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
}
