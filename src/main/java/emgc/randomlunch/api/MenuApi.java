package emgc.randomlunch.api;

import emgc.randomlunch.dto.MenuInfoDto;
import emgc.randomlunch.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
@Slf4j
@Validated
public class MenuApi {

    private final MenuService menuService;

    @PostMapping("/upload")
    public void addMenu(
            @RequestBody @Valid MenuInfoDto menuInfoDto,
            @NotNull(message = "메뉴 추가는 로그인이 필요합니다.") Principal principal
    ) {
        log.info("Received a request to create a menu {} from user {}", menuInfoDto.getName(), principal.getName());
        menuService.addMenu(menuInfoDto);
    }

    @GetMapping("/list/{id}")
    public List<MenuInfoDto> getAllMenus(
            @PathVariable
            @NotNull(message = "조회할 메뉴들의 음식점 아이디는 필수값 입니다.")
            @Positive(message = "조회할 메뉴들의 음식점 아이디는 양의 정수이어야 합니다.")
            @Min(value = 1L, message = "조회할 메뉴들의 음식점 아이디는 최소 1 이상이어야 합니다.") Long id
    ) {
        log.info("Retrieving menus for restaurant {} ", id);
        return menuService.getMenuList(id);
    }

    @PostMapping("/edit")
    public void editMenu(
            @RequestBody @Valid MenuInfoDto menuInfoDto,
            @NotNull(message = "메뉴 수정은 로그인이 필요합니다.") Principal principal
    ) {
        log.info("Received a edit request for menu id {} from user {}", menuInfoDto.getId(), principal.getName());
        menuService.editMenu(menuInfoDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteMenu(
            @PathVariable
            @NotNull(message = "삭제할 메뉴 아이디는 필수값 입니다.")
            @Positive(message = "삭제할 메뉴 아이디는 양의 정수이어야 합니다.")
            @Min(value = 1L, message = "삭제할 메뉴 아이디는 최소 1 이상이어야 합니다.") Long id,
            @NotNull(message = "메뉴 삭제는 로그인이 필요합니다.") Principal principal
    ) {
        log.info("Received a delete request for menu id {} from user {}", id, principal.getName());
        menuService.deleteMenu(id);
    }


}
