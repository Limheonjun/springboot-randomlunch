package emgc.randomlunch.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import emgc.randomlunch.config.RestDocsConfig;
import emgc.randomlunch.dto.CategoryInfoDto;
import emgc.randomlunch.dto.MenuInfoDto;
import emgc.randomlunch.dto.RestaurantInfoDto;
import emgc.randomlunch.entity.Category;
import emgc.randomlunch.entity.Restaurant;
import emgc.randomlunch.repository.CategoryRepository;
import emgc.randomlunch.repository.RestaurantRepository;
import emgc.randomlunch.security.repository.ResourcesRepository;
import emgc.randomlunch.service.MenuService;
import emgc.randomlunch.service.RestaurantService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Import(RestDocsConfig.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext
class MenuApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MenuService menuService;

    private Restaurant restaurant;

    @BeforeAll
    void setRestaurant(){
        CategoryInfoDto category = new CategoryInfoDto(null, "디저트");
        Category save = categoryRepository.save(new Category(category));

        restaurant = Restaurant.builder()
                .category(save)
                .address("서울특별시 강남구")
                .name("에슐리")
                .openTime(LocalTime.of(9,0))
                .closeTime(LocalTime.of(23,0))
                .build();

        restaurant = restaurantRepository.save(restaurant);
    }

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext,
               RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    @Order(1)
    void addMenu() throws Exception {
        MenuInfoDto menu = new MenuInfoDto(null, restaurant.getId(), "보쌈정식", 8000);
        String content = objectMapper.writeValueAsString(menu);

        mockMvc.perform(post("/menu/upload")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(document("addMenu",
                        requestFields(
                                fieldWithPath("id").ignored().type(JsonFieldType.NUMBER).description("Id of menu"),
                                fieldWithPath("restaurantId").ignored().type(JsonFieldType.NUMBER).description("Id of the restaurant to which the category belongs"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("Name of the menu to register"),
                                fieldWithPath("price").type(JsonFieldType.NUMBER).description("Price of the menu to register")
                        )
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @Order(2)
    void getAllMenus() throws Exception {
        mockMvc.perform(get("/menu/list")
                .content(objectMapper.writeValueAsString(new RestaurantInfoDto(restaurant)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(document("getAllMenus",
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("Id of menu"),
                                fieldWithPath("[].restaurantId").type(JsonFieldType.NUMBER).description("Id of the restaurant to which the menu belongs"),
                                fieldWithPath("[].name").type(JsonFieldType.STRING).description("Name of the menu"),
                                fieldWithPath("[].price").type(JsonFieldType.NUMBER).description("Price of the menu")
                        )
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("보쌈정식"))
                .andExpect(jsonPath("$[0].price").value(8000))
        ;
    }

    @Test
    @Order(3)
    void editMenu() throws Exception{
        List<MenuInfoDto> menuList = menuService.getMenuList(restaurant);
        MenuInfoDto menuInfoDto = menuList.get(0);
        menuInfoDto.setName("고등어찜");
        menuInfoDto.setPrice(7000);

        mockMvc.perform(post("/menu/edit")
                .content(objectMapper.writeValueAsString(menuInfoDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(document("editMenu",
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("Id of menu"),
                                fieldWithPath("restaurantId").type(JsonFieldType.NUMBER).description("Id of the restaurant to which the category belongs"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("Name of the menu to edit"),
                                fieldWithPath("price").type(JsonFieldType.NUMBER).description("Price of the menu to edit")
                        )
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @Order(4)
    void deleteMenu() throws Exception {
        List<MenuInfoDto> menuList = menuService.getMenuList(restaurant);
        MenuInfoDto menuInfoDto = menuList.get(0);

        mockMvc.perform(delete("/menu/delete")
                .content(objectMapper.writeValueAsString(menuInfoDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(document("deleteMenu",
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("Id of menu to delete"),
                                fieldWithPath("restaurantId").ignored().type(JsonFieldType.NUMBER).description("Id of the restaurant to which the category belongs"),
                                fieldWithPath("name").ignored().type(JsonFieldType.STRING).description("Name of the menu to edit"),
                                fieldWithPath("price").ignored().type(JsonFieldType.NUMBER).description("Price of the menu to edit")
                        )
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }
}