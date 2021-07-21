package emgc.randomlunch.api;

import emgc.randomlunch.dto.MenuInfoDto;
import emgc.randomlunch.dto.RestaurantInfoDto;
import emgc.randomlunch.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
@Slf4j
public class MenuApi {

    private final MenuService menuService;

    @PostMapping("/upload")
    public void addMenu(@RequestBody MenuInfoDto menuInfoDto, Principal principal) {
        log.info("Received a request to create a menu {} from user {}", menuInfoDto.getName(), principal.getName());
        menuService.addMenu(menuInfoDto);
    }

    @GetMapping("/list/{id}")
    public List<MenuInfoDto> getAllMenus(@PathVariable("id") Long restaurantId) {
        log.info("Retrieving menus for restaurant {} ", restaurantId);
        return menuService.getMenuList(restaurantId);
    }

    @PostMapping("/edit")
    public void editMenu(@RequestBody MenuInfoDto menuInfoDto, Principal principal) {
        log.info("Received a edit request for menu id {} from user {}", menuInfoDto.getId(), principal.getName());
        menuService.editMenu(menuInfoDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteMenu(@PathVariable Long id, Principal principal) {
        log.info("Received a delete request for menu id {} from user {}", id, principal.getName());
        menuService.deleteMenu(id);
    }


}
