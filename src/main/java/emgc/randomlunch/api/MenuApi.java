package emgc.randomlunch.api;

import emgc.randomlunch.dto.MenuInfoDto;
import emgc.randomlunch.entity.Menu;
import emgc.randomlunch.entity.Restaurant;
import emgc.randomlunch.service.MenuService;
import emgc.randomlunch.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuApi {

    private final RestaurantService restaurantService;
    private final MenuService menuService;

    @PostMapping("/upload")
    public void uploadMenu(@RequestBody MenuInfoDto menuInfoDto) {
        Restaurant findRestaurant = restaurantService.getRestaurant(menuInfoDto.getRestaurantId());
        Menu menu = menuInfoDto.makeMenu(findRestaurant);
        menuService.addMenu(menu);
    }

    @GetMapping("/list")
    public List<MenuInfoDto> getAllMenus(@RequestParam Long restaurantId) {
        Restaurant findRestaurant = restaurantService.getRestaurant(restaurantId);
        List<MenuInfoDto> menuList = menuService.getMenuList(findRestaurant);
        return menuList;
    }

    @PostMapping("/edit")
    public void editMenu(@RequestBody MenuInfoDto menuInfoDto) {
        menuService.editMenu(menuInfoDto);
    }

    @DeleteMapping("/delete")
    public void deleteMenu(@RequestParam Long menuId) {
        menuService.deleteMenu(menuId);
    }


}
