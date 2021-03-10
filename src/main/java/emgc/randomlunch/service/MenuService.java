package emgc.randomlunch.service;

import emgc.randomlunch.dto.MenuInfoDto;
import emgc.randomlunch.entity.Menu;
import emgc.randomlunch.entity.Restaurant;
import emgc.randomlunch.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository repository;

    //메뉴 목록 조회
    public List<MenuInfoDto> getMenuList(Restaurant restaurant) {
        List<MenuInfoDto> menuList = new ArrayList<>();
        List<Menu> findMenuList = repository.findByRestaurantOrderByPriceAsc(restaurant);
        menuList = findMenuList.stream()
                .map(MenuInfoDto::new)
                .collect(Collectors.toList());
        return menuList;
    }

    //메뉴 추가
    public void addMenu(Menu menu) {
        repository.save(menu);
    }

    //메뉴 수정
    public void editMenu(MenuInfoDto menuInfoDto) {
        Menu menu = getMenu(menuInfoDto.getId());
        menu.setName(menuInfoDto.getName());
        menu.setPrice(menuInfoDto.getPrice());
        repository.save(menu);
    }

    //메뉴 삭제
    public void deleteMenu(Long id) {
        Menu menu = repository.findById(id).orElseThrow();
        repository.delete(menu);
    }

    //메뉴 찾기
    public Menu getMenu(Long id) {
        Menu menu = repository.findById(id).orElseThrow();
        return menu;
    }
}
