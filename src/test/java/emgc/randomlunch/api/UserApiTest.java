package emgc.randomlunch.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import emgc.randomlunch.config.RestDocsConfig;
import emgc.randomlunch.dto.UserDto;
import emgc.randomlunch.enums.Gender;
import emgc.randomlunch.security.domain.User;
import emgc.randomlunch.security.provider.JwtAuthenticationProvider;
import emgc.randomlunch.security.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Import(RestDocsConfig.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    private UserDto userDto;

    @BeforeAll
    void makeUser(){
        userDto = UserDto.builder()
                .email("example@test.com")
                .gender(Gender.MALE)
                .name("Lim")
                .password(passwordEncoder.encode("1234"))
                .phoneNumber("01022225555")
                .build();
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
    void join() throws Exception {
        mockMvc.perform(post("/user/join")
                .content(objectMapper.writeValueAsString(userDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(document("joinUser",
                        requestFields(
                                fieldWithPath("id").ignored().type(JsonFieldType.NUMBER).description("Id of user"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("Password of user"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("Email of user"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("Name of user"),
                                fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("Phone number of user"),
                                fieldWithPath("gender").type(JsonFieldType.STRING).description("Gender of user"),
                                fieldWithPath("role").ignored().type(JsonFieldType.STRING).description("Role of user")
                        )
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());

        List<User> users = userRepository.findAll();
        User user = users.get(0);
        assertEquals(user.getEmail(), "example@test.com");
        assertEquals(user.getGender(), Gender.MALE);
        assertEquals(user.getName(), "Lim");
//        assertTrue(passwordEncoder.matches("1234", user.getPassword()));
        assertEquals(user.getPhoneNumber(), "01022225555");

    }

    @Test
    @Order(2)
    void login() throws Exception {
        mockMvc.perform(post("/user/login")
                .content(objectMapper.writeValueAsString(userDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(document("loginUser",
                        requestFields(
                                fieldWithPath("id").ignored().type(JsonFieldType.NUMBER).description("Id of user"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("Password of user"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("Email of user"),
                                fieldWithPath("name").ignored().type(JsonFieldType.STRING).description("Name of user"),
                                fieldWithPath("phoneNumber").ignored().type(JsonFieldType.STRING).description("Phone number of user"),
                                fieldWithPath("gender").ignored().type(JsonFieldType.STRING).description("Gender of user"),
                                fieldWithPath("role").ignored().type(JsonFieldType.STRING).description("Role of user")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("Id of user"),
                                fieldWithPath("password").ignored().type(JsonFieldType.NULL).description("Password of user"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("Email of user"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("Name of user"),
                                fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("Phone number of user"),
                                fieldWithPath("gender").type(JsonFieldType.STRING).description("Gender of user"),
                                fieldWithPath("role").ignored().type(JsonFieldType.STRING).description("Role of user")
                        )
                ))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(cookie().exists("X-AUTH-TOKEN"))
                .andExpect(jsonPath("$.email").value("example@test.com"))
                .andExpect(jsonPath("$.name").value("Lim"))
                .andExpect(jsonPath("$.phoneNumber").value("01022225555"))
                .andExpect(jsonPath("$.gender").value(Gender.MALE.name()))
                .andExpect(jsonPath("$.role").value("ROLE_USER"));
    }

    @Test
    @Order(3)
    @Transactional
    void getInfo() throws Exception {
        String token = jwtAuthenticationProvider.createToken("example@test.com", List.of("ROLE_USER"));
        Authentication authentication = jwtAuthenticationProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(get("/user/info")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(document("getInfo",
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("Id of user"),
                                fieldWithPath("password").ignored().type(JsonFieldType.NULL).description("Password of user"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("Email of user"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("Name of user"),
                                fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("Phone number of user"),
                                fieldWithPath("gender").type(JsonFieldType.STRING).description("Gender of user"),
                                fieldWithPath("role").type(JsonFieldType.STRING).description("Role of user")
                        )
                ))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value("example@test.com"))
                .andExpect(jsonPath("$.name").value("Lim"))
                .andExpect(jsonPath("$.phoneNumber").value("01022225555"))
                .andExpect(jsonPath("$.gender").value(Gender.MALE.name()))
                .andExpect(jsonPath("$.role").value("ROLE_USER"));

    }

    @Test
    @Order(4)
    @Transactional
    void update() throws Exception{
        List<User> users = userRepository.findAll();
        User user = users.get(0);
        UserDto userDto = new UserDto(user);
        userDto.setName("Kim");
        userDto.setPhoneNumber("01012345678");

        mockMvc.perform(post("/user/update")
                .content(objectMapper.writeValueAsString(userDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(document("updateUser",
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("Id of user"),
                                fieldWithPath("password").type(JsonFieldType.NULL).description("Password of user"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("Email of user"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("Name of user"),
                                fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("Phone number of user"),
                                fieldWithPath("gender").type(JsonFieldType.STRING).description("Gender of user"),
                                fieldWithPath("role").type(JsonFieldType.STRING).description("Role of user")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("Id of user"),
                                fieldWithPath("password").type(JsonFieldType.NULL).description("Password of user"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("Email of user"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("Name of user"),
                                fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("Phone number of user"),
                                fieldWithPath("gender").type(JsonFieldType.STRING).description("Gender of user"),
                                fieldWithPath("role").type(JsonFieldType.STRING).description("Role of user")
                        )
                ))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Kim"))
                .andExpect(jsonPath("$.phoneNumber").value("01012345678"));
    }

    @Test
    @Order(5)
    void logout() throws Exception{
        ResponseCookie cookie = ResponseCookie.from("X-AUTH-TOKEN", "token")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("None")
                .domain("randomlunch.ga")
                .build();


        String emptyString = "";

        mockMvc.perform(post("/user/logout")
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(document("logoutUser")
        )
                .andExpect(status().isOk())
                .andExpect(cookie().value("X-AUTH-TOKEN", emptyString));
    }

}