package demidov.alfatest.exceptionsTests;

import com.github.tomakehurst.wiremock.WireMockServer;
import demidov.alfatest.testconfig.WireMockConfig;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource("classpath:application-test.properties")
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class ControllerExceptionsTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Value("${comparingCurrency}")
    private String validCurrency;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    public void testCommonAppException() throws Exception {
        validCurrency = "";
        String uri = "/gif";
        mockMvc.perform(get(uri).param("currency", validCurrency))
                .andDo(document(uri.replace("/", "\\")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.[0]", Matchers.containsString("Request parameters for exchange service must not be null")));
    }

   @Test
    public void testMissingServletRequestParameterException() throws Exception {
        String uri = "/gif";
        mockMvc.perform(get(uri))
                .andDo(document(uri.replace("/", "\\")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.[0]", Matchers.containsString("parameter is missing")));
    }


    @Test
    public void testHttpRequestMethodNotSupportedException() throws Exception {
        validCurrency = "RUB";
        String uri = "/gif";
        mockMvc.perform(post(uri).param("currency", validCurrency))
                .andDo(document(uri.replace("/", "\\")))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath("$.errors.[0]", Matchers.containsString("method is not supported")));
    }
}
