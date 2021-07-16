package emgc.randomlunch.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import emgc.randomlunch.config.RestDocsConfig;
import emgc.randomlunch.dto.QuestionInfoDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
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
class QuestionApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void uploadQuestion() throws Exception{
        QuestionInfoDto questionInfoDto = new QuestionInfoDto(null, "example@test.com", "궁금합니다");

        mockMvc.perform(post("/question/upload")
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