package emgc.randomlunch.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import emgc.randomlunch.config.RestDocsConfig;
import emgc.randomlunch.dto.CategoryInfoDto;
import emgc.randomlunch.dto.RestaurantInfoDto;
import emgc.randomlunch.entity.Category;
import emgc.randomlunch.enums.Gender;
import emgc.randomlunch.repository.CategoryRepository;
import emgc.randomlunch.security.domain.Role;
import emgc.randomlunch.security.domain.User;
import emgc.randomlunch.security.domain.UserRole;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
class RestaurantApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category category;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private TestingAuthenticationToken testingAuthenticationToken;

    @BeforeAll
    void beforeAll(){
        category = Category.builder()
                .name("테스트카테고리")
                .build();

        categoryRepository.save(category);

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
    void uploadRestaurant() throws Exception{
        RestaurantInfoDto restaurantInfoDto = RestaurantInfoDto.builder()
                .address("서울특별시 강남구")
                .categoryId(category.getId())
                .closeTime(LocalTime.of(22,0,0))
                .openTime(LocalTime.of(9,0,0))
                .name("홍콩반점")
                .build();

        mockMvc.perform(post("/restaurant/upload")
                .principal(testingAuthenticationToken)
                .content(objectMapper.writeValueAsString(restaurantInfoDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(document("addRestaurant",
                        requestFields(
                                fieldWithPath("id").ignored().type(JsonFieldType.NUMBER).description("Id of restaurant"),
                                fieldWithPath("menuList").ignored().type(JsonFieldType.ARRAY).description("Restaurant menu list"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("Name of restaurant"),
                                fieldWithPath("openTime").type(JsonFieldType.STRING).description("Restaurant opening hours"),
                                fieldWithPath("closeTime").type(JsonFieldType.STRING).description("Restaurant closing  hours"),
                                fieldWithPath("categoryId").type(JsonFieldType.NUMBER).description("Category the restaurant belongs to"),
                                fieldWithPath("address").type(JsonFieldType.STRING).description("Address of the restaurant"),
                                fieldWithPath("hashtags").ignored().type(JsonFieldType.ARRAY).description("Restaurant hashtags")
                        )
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @Order(2)
    void getAllRestaurants() throws Exception {
        mockMvc.perform(get("/restaurant/list")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(document("getRestaurantList",
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("Id of restaurant"),
                                fieldWithPath("[].menuList").type(JsonFieldType.ARRAY).description("Restaurant menu list"),
                                fieldWithPath("[].name").type(JsonFieldType.STRING).description("Name of restaurant"),
                                fieldWithPath("[].openTime").type(JsonFieldType.STRING).description("Restaurant opening hours"),
                                fieldWithPath("[].closeTime").type(JsonFieldType.STRING).description("Restaurant closing  hours"),
                                fieldWithPath("[].categoryId").type(JsonFieldType.NUMBER).description("Category the restaurant belongs to"),
                                fieldWithPath("[].address").type(JsonFieldType.STRING).description("Address of the restaurant"),
                                fieldWithPath("[].hashtags").type(JsonFieldType.ARRAY).description("Restaurant hashtags")
                        )
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("홍콩반점"))
                .andExpect(jsonPath("$[0].address").value("서울특별시 강남구"))
                .andExpect(jsonPath("$[0].openTime").value("09:00:00"))
                .andExpect(jsonPath("$[0].closeTime").value("22:00:00"))
        ;
    }

    @Test
    @Order(3)
    void getRestaurantListByCategory() throws Exception{
        CategoryInfoDto categoryInfoDto = new CategoryInfoDto(category);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/restaurant/list/{id}", categoryInfoDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(document("getRestaurantListByCategory",
                        pathParameters(
                                parameterWithName("id").description("Id of category")
                        ),
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("Id of restaurant"),
                                fieldWithPath("[].menuList").type(JsonFieldType.ARRAY).description("Restaurant menu list"),
                                fieldWithPath("[].name").type(JsonFieldType.STRING).description("Name of restaurant"),
                                fieldWithPath("[].openTime").type(JsonFieldType.STRING).description("Restaurant opening hours"),
                                fieldWithPath("[].closeTime").type(JsonFieldType.STRING).description("Restaurant closing  hours"),
                                fieldWithPath("[].categoryId").type(JsonFieldType.NUMBER).description("Category the restaurant belongs to"),
                                fieldWithPath("[].address").type(JsonFieldType.STRING).description("Address of the restaurant"),
                                fieldWithPath("[].hashtags").type(JsonFieldType.ARRAY).description("Restaurant hashtags")
                        )
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("홍콩반점"))
                .andExpect(jsonPath("$[0].address").value("서울특별시 강남구"))
                .andExpect(jsonPath("$[0].openTime").value("09:00:00"))
                .andExpect(jsonPath("$[0].closeTime").value("22:00:00"))
        ;
    }

    @Test
    @Order(4)
    void editRestaurant() throws Exception{
        MvcResult mvcResult = mockMvc.perform(get("/restaurant/list")).andReturn();
        List<RestaurantInfoDto> restaurantInfoDtoList = (List<RestaurantInfoDto>) objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<RestaurantInfoDto>>() {});
        RestaurantInfoDto restaurantInfoDto = restaurantInfoDtoList.get(0);
        restaurantInfoDto.setName("빽다방");
        restaurantInfoDto.setAddress("서울특별시 관악구");

        mockMvc.perform(post("/restaurant/edit")
                .principal(testingAuthenticationToken)
                .content(objectMapper.writeValueAsString(restaurantInfoDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(document("editRestaurant",
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("Id of restaurant"),
                                fieldWithPath("menuList").ignored().type(JsonFieldType.ARRAY).description("Restaurant menu list"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("Name of restaurant"),
                                fieldWithPath("openTime").type(JsonFieldType.STRING).description("Restaurant opening hours"),
                                fieldWithPath("closeTime").type(JsonFieldType.STRING).description("Restaurant closing  hours"),
                                fieldWithPath("categoryId").type(JsonFieldType.NUMBER).description("Category the restaurant belongs to"),
                                fieldWithPath("address").type(JsonFieldType.STRING).description("Address of the restaurant"),
                                fieldWithPath("hashtags").ignored().type(JsonFieldType.ARRAY).description("Restaurant hashtags")
                        )
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
        ;
    }

    @Test
    @Order(5)
    void deleteRestaurant() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/restaurant/list")).andReturn();
        List<RestaurantInfoDto> restaurantInfoDtoList = (List<RestaurantInfoDto>) objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<RestaurantInfoDto>>() {});
        RestaurantInfoDto restaurantInfoDto = restaurantInfoDtoList.get(0);

        mockMvc.perform(RestDocumentationRequestBuilders.delete("/restaurant/delete/{id}", restaurantInfoDto.getId())
                .principal(testingAuthenticationToken)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(document("deleteRestaurant",
                        pathParameters(
                                parameterWithName("id").description("Restaurant id you want to delete")
                        )
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
        ;
    }
}