package emgc.randomlunch.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import emgc.randomlunch.config.RestDocsConfig;
import emgc.randomlunch.dto.CategoryInfoDto;
import emgc.randomlunch.dto.MenuInfoDto;
import emgc.randomlunch.dto.RestaurantInfoDto;
import emgc.randomlunch.entity.Category;
import emgc.randomlunch.entity.Restaurant;
import emgc.randomlunch.enums.Gender;
import emgc.randomlunch.repository.CategoryRepository;
import emgc.randomlunch.repository.RestaurantRepository;
import emgc.randomlunch.security.domain.Role;
import emgc.randomlunch.security.domain.User;
import emgc.randomlunch.security.domain.UserRole;
import emgc.randomlunch.service.MenuService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Import(RestDocsConfig.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Restaurant restaurant;

    private TestingAuthenticationToken testingAuthenticationToken;

    @BeforeAll
    void beforeAll(){
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

        Role role = Role.builder()
                .roleDesc("관리자")
                .roleName("ROLE_ADMIN")
                .build();

        UserRole userRole = UserRole.builder()
                .role(role)
                .build();

        User user = User.builder()
                .email("test@example.com")
                .gender(Gender.MALE)
                .password(passwordEncoder.encode("1234"))
                .phoneNumber("01012345678")
                .userRole(Set.of(userRole))
                .build();

        testingAuthenticationToken = new TestingAuthenticationToken(user, null);
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
                .principal(testingAuthenticationToken)
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
        RestaurantInfoDto restaurantInfoDto = new RestaurantInfoDto(restaurant);
        restaurantInfoDto.getId();

        mockMvc.perform(RestDocumentationRequestBuilders.get("/menu/list/{id}", restaurantInfoDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(document("getAllMenus",
                        pathParameters(
                                parameterWithName("id").description("Id of the restaurant to get the menu from")
                        ),
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
                .principal(testingAuthenticationToken)
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

        mockMvc.perform(delete("/menu/delete/{id}", menuInfoDto.getId())
                .principal(testingAuthenticationToken)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(document("deleteMenu",
                        pathParameters(
                                parameterWithName("id").description("Id of menu to delete")
                        )
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }
}