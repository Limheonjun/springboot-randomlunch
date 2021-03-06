package emgc.randomlunch.api;

import emgc.randomlunch.dto.MenuInfoDto;
import emgc.randomlunch.entity.Menu;
import emgc.randomlunch.entity.Restaurant;
import emgc.randomlunch.repository.MenuRepository;
import emgc.randomlunch.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuApi {

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    @PostMapping("/upload")
    public void uploadMenu(@RequestBody MenuInfoDto menuInfoDto) {

        Restaurant findRestaurant = restaurantRepository.findById(menuInfoDto.getRestaurantId()).get();

        Menu menu = Menu.builder()
                    .name(menuInfoDto.getName())
                    .price(menuInfoDto.getPrice())
                    .restaurant(findRestaurant)
                    .build();

        menuRepository.save(menu);
    }

    @GetMapping("/list")
    public List<MenuInfoDto> getAllMenus(@RequestParam Long restaurantId) {
        System.out.println("restaurantId " + restaurantId);
        Restaurant findRestaurant = restaurantRepository.findById(restaurantId).get();
        List<Menu> menuList = menuRepository.findByRestaurantOrderByPriceAsc(findRestaurant);
        List<MenuInfoDto> menuInfoDtoList = new ArrayList<>();
        for(Menu menu : menuList) {
            menuInfoDtoList.add(MenuInfoDto.builder()
                    .menuId(menu.getId())
                    .restaurantId(menu.getRestaurant().getId())
                    .name(menu.getName())
                    .price(menu.getPrice())
                    .build());
        }

        return menuInfoDtoList;
    }

    @PostMapping("/edit")
    public MenuInfoDto editMenu(@RequestBody MenuInfoDto menuInfoDto) {
        System.out.println("edit " + menuInfoDto.getMenuId());
        Menu menu = menuRepository.findById(menuInfoDto.getMenuId()).get();
        menu.setName(menuInfoDto.getName());
        menu.setPrice(menuInfoDto.getPrice());
        Menu save = menuRepository.save(menu);
        return new MenuInfoDto(save);
    }

    @DeleteMapping("/delete")
    public void deleteMenu(@RequestParam Long menuId) {
        Menu findMenu = menuRepository.findById(menuId).get();
        menuRepository.delete(findMenu);
        System.out.println("menuId" + menuId);
    }


}
