package com.example.policyassistant.eval;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.policyassistant.api.dto.PolicyAnswerResponse;
import com.example.policyassistant.api.dto.PolicyQuestionRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class PolicyAssistantEvaluationTest {

    private static final String EVAL_CASES = "/evaluation/policy-assistant-eval-cases.json";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    static Stream<EvalCase> evalCases() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream input = PolicyAssistantEvaluationTest.class.getResourceAsStream(EVAL_CASES)) {
            assertThat(input).as("evaluation dataset is packaged").isNotNull();
            return mapper.readValue(input, new TypeReference<List<EvalCase>>() {}).stream();
        }
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("evalCases")
    void satisfiesGovernedEvaluationCase(EvalCase evalCase) throws Exception {
        PolicyQuestionRequest request = new PolicyQuestionRequest(evalCase.question(), evalCase.userId());

        var result = mockMvc.perform(post("/api/v1/policy/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is(evalCase.expectedStatus()))
                .andReturn();

        String body = result.getResponse().getContentAsString();
        if (evalCase.expectedStatus() != 200) {
            assertProblemResponse(body, evalCase);
            return;
        }

        PolicyAnswerResponse response = objectMapper.readValue(body, PolicyAnswerResponse.class);
        assertThat(response.confidence()).isEqualTo(evalCase.expectedConfidence());
        assertThat(response.citations().stream().map(PolicyAnswerResponse.Citation::documentId).toList())
                .containsAll(evalCase.expectedDocumentIds());

        for (String fragment : evalCase.expectedAnswerContains()) {
            assertThat(response.answer()).containsIgnoringCase(fragment);
        }
        for (String warning : evalCase.expectedWarningsContains()) {
            assertThat(response.warnings()).contains(warning);
        }
    }

    private void assertProblemResponse(String body, EvalCase evalCase) throws IOException {
        JsonNode problem = objectMapper.readTree(body);
        assertThat(problem.path("title").asText()).isEqualTo(evalCase.expectedProblemTitle());
    }

    record EvalCase(
            String id,
            String category,
            String question,
            String userId,
            int expectedStatus,
            String expectedConfidence,
            List<String> expectedDocumentIds,
            List<String> expectedAnswerContains,
            List<String> expectedWarningsContains,
            String expectedProblemTitle) {

        @Override
        public String toString() {
            return id + " - " + category;
        }
    }
}
