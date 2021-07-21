package emgc.randomlunch.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import emgc.randomlunch.config.RestDocsConfig;
import emgc.randomlunch.dto.CategoryInfoDto;
import emgc.randomlunch.enums.Gender;
import emgc.randomlunch.security.domain.Role;
import emgc.randomlunch.security.domain.User;
import emgc.randomlunch.security.domain.UserRole;
import emgc.randomlunch.service.CategoryService;
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
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Set;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
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
class CategoryApiTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private TestingAuthenticationToken testingAuthenticationToken;

    @BeforeAll
    void beforeAll(){
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
    @WithMockUser(roles = {"ADMIN"})
    void addCategory() throws Exception {
        CategoryInfoDto category = new CategoryInfoDto(null, "디저트");
        String content = objectMapper.writeValueAsString(category);

        mockMvc.perform(post("/category/upload")
                .principal(testingAuthenticationToken)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(document("addCategory",
                        requestFields(
                                fieldWithPath("id").ignored().type(JsonFieldType.NUMBER).description("Id of Category"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("Name of Category")
                        )
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @Order(2)
    void getCategoryList() throws Exception {

        mockMvc.perform(get("/category/list")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(document("getCategoryList",
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("Id of Category"),
                                fieldWithPath("[].name").type(JsonFieldType.STRING).description("Name of Category")
                        )
                ))
                .andExpect(status().isOk())
        ;

    }

    @Test
    @Order(3)
    void editCategoryInfo() throws Exception {
        List<CategoryInfoDto> categoryList = categoryService.getCategoryList();
        CategoryInfoDto categoryInfoDto = categoryList.get(0);
        categoryInfoDto.setName("맛있는디저트");

        mockMvc.perform(post("/category/edit")
                .principal(testingAuthenticationToken)
                .content(objectMapper.writeValueAsString(categoryInfoDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(document("editCategoryInfo",
                        requestFields(
                                fieldWithPath("id").ignored().type(JsonFieldType.NUMBER).description("Id of the category to be renamed"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("Category name you want to change")
                        )
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());

    }

    @Test
    @Order(4)
    void deleteCategory() throws Exception {
        List<CategoryInfoDto> categoryList = categoryService.getCategoryList();
        CategoryInfoDto categoryInfoDto = categoryList.get(0);

        mockMvc.perform(delete("/category/delete/{id}", categoryInfoDto.getId())
                .principal(testingAuthenticationToken)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(document("deleteCategory",
                        pathParameters(
                                parameterWithName("id").description("Category id you want to delete")
                        )
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }
}