package emgc.randomlunch.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import emgc.randomlunch.config.RestDocsConfig;
import emgc.randomlunch.dto.QuestionInfoDto;
import emgc.randomlunch.enums.Gender;
import emgc.randomlunch.security.domain.Role;
import emgc.randomlunch.security.domain.User;
import emgc.randomlunch.security.domain.UserRole;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Set;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Import(RestDocsConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class QuestionApiTest {

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
                .name("Lim")
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
    void uploadQuestion() throws Exception{
        QuestionInfoDto questionInfoDto = new QuestionInfoDto(null, "example@test.com", "궁금합니다");

        this.mockMvc.perform(post("/question/upload")
                .principal(testingAuthenticationToken)
                .content(objectMapper.writeValueAsString(questionInfoDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(document("uploadQuestion",
                        requestFields(
                                fieldWithPath("id").ignored().type(JsonFieldType.NUMBER).description("Id of question"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("Email to be replied to"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("Questions to ask")
                        )
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }
}