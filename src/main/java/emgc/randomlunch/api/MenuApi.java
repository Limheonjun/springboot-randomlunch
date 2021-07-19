package emgc.randomlunch.api;

import emgc.randomlunch.dto.MenuInfoDto;
import emgc.randomlunch.dto.RestaurantInfoDto;
import emgc.randomlunch.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuApi {

    private final MenuService menuService;

    @PostMapping("/upload")
    public void addMenu(@RequestBody MenuInfoDto menuInfoDto) {
        menuService.addMenu(menuInfoDto);
    }

    @GetMapping("/list")
    public List<MenuInfoDto> getAllMenus(RestaurantInfoDto restaurantInfoDto) {
        return menuService.getMenuList(restaurantInfoDto);
    }

    @PostMapping("/edit")
    public void editMenu(@RequestBody MenuInfoDto menuInfoDto) {
        menuService.editMenu(menuInfoDto);
    }

    @DeleteMapping("/delete")
    public void deleteMenu(@RequestBody MenuInfoDto menuInfoDto) {
        menuService.deleteMenu(menuInfoDto);
    }


}
