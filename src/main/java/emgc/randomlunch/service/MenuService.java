package emgc.randomlunch.service;

import emgc.randomlunch.dto.MenuInfoDto;
import emgc.randomlunch.dto.RestaurantInfoDto;
import emgc.randomlunch.entity.Menu;
import emgc.randomlunch.entity.Restaurant;
import emgc.randomlunch.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantService restaurantService;

    //메뉴 목록 조회
    public List<MenuInfoDto> getMenuList(Restaurant restaurant) {
        List<Menu> findMenuList = menuRepository.findByRestaurantOrderByPriceAsc(restaurant);
        return findMenuList.stream().map(MenuInfoDto::new).collect(Collectors.toList());
    }

    public List<MenuInfoDto> getMenuList(RestaurantInfoDto restaurantInfoDto) {
        Restaurant restaurant = restaurantService.getRestaurant(restaurantInfoDto.getId());
        return getMenuList(restaurant);
    }

    //메뉴 추가
    public Menu addMenu(MenuInfoDto menuInfoDto) {
        Restaurant restaurant = restaurantService.getRestaurant(menuInfoDto.getRestaurantId());
        Menu menu = Menu.builder()
                .restaurant(restaurant)
                .price(menuInfoDto.getPrice())
                .name(menuInfoDto.getName())
                .build();
        return menuRepository.save(menu);
    }

    //메뉴 수정
    public Menu editMenu(MenuInfoDto menuInfoDto) {
        Menu menu = getMenu(menuInfoDto);
        menu.setName(menuInfoDto.getName());
        menu.setPrice(menuInfoDto.getPrice());
        return menu;
    }

    //메뉴 삭제
    public void deleteMenu(MenuInfoDto menuInfoDto) {
        Menu menu = menuRepository.findById(menuInfoDto.getId()).orElseThrow();
        menuRepository.delete(menu);
    }

    //메뉴 찾기
    public Menu getMenu(MenuInfoDto menuInfoDto) {
        return menuRepository.findById(menuInfoDto.getId()).orElseThrow();
    }
}
