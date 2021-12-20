package demidov.alfatest.controllersTests;

import com.github.tomakehurst.wiremock.WireMockServer;
import demidov.alfatest.testconfig.ClientsMocks;
import demidov.alfatest.testconfig.WireMockConfig;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties
@TestPropertySource("classpath:application-test.properties")
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@ContextConfiguration(classes = {WireMockConfig.class})
public class ExchangeControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    private WireMockServer mockExchangeServer;

    private MockMvc mockMvc;

    @Value("${comparingCurrency}")
    private String validCurrency;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) throws IOException {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();

        ClientsMocks.setupRecentExchangeResponse(mockExchangeServer);
        ClientsMocks.setupHistoricalExchangeResponse(mockExchangeServer);
        ClientsMocks.setupGiphyResponse(mockExchangeServer);
    }

    @Test
    public void setWebApplicationContext() {
        ServletContext servletContext = webApplicationContext.getServletContext();
        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(webApplicationContext.getBean("exchangeController"));
    }

    @Test
    public void returnsIndexPage() throws Exception {
        String uri = "/";
        mockMvc.perform(get(uri))
                .andDo(document(uri.replace("/", "\\")))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void gifEndpointReturnsCorrectResponseType() throws Exception {
        String uri = "/gif";
        mockMvc.perform(get(uri).param("currency", validCurrency))
                .andDo(document(uri.replace("/", "\\")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/html;charset=UTF-8")));
    }

    @Test
    public void gifEndpointReturnsCorrectResponse() throws Exception {
        String uri = "/gif";
        mockMvc.perform(get(uri).param("currency", validCurrency))
                .andDo(document(uri.replace("/", "\\")))
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(content().string(Matchers.containsString("<title>Alfa Test Result</title>")));
    }


}
