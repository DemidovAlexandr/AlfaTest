package demidov.alfatest.clientsTests;

import com.github.tomakehurst.wiremock.WireMockServer;
import demidov.alfatest.feignclients.GifClient;
import demidov.alfatest.testconfig.ClientsMocks;
import demidov.alfatest.testconfig.WireMockConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@ContextConfiguration(classes = {WireMockConfig.class})
@TestPropertySource("classpath:application-test.properties")
public class GifClientTest {

    @Autowired
    private WireMockServer mockExchangeServer;

    @Autowired
    private GifClient gifClient;

    @Value("${giphyAppId}")
    private String api_key;

    @Value("${giphyRating}")
    private String rating;

    @Value("${richQuery}")
    private String query;


    @BeforeEach
    void setUp() throws IOException {
        ClientsMocks.setupGiphyResponse(mockExchangeServer);
    }

    @Test
    public void whenGet_thenGifsShouldBeReturned() {
        assertFalse(gifClient.getGif(api_key, query, rating).getOriginalURL().isEmpty());

    }

    @Test
    public void whenGet_thenCorrectRatesShouldBeReturned() {
        assertTrue(gifClient.getGif(api_key, query, rating).getOriginalURL().contains("https://giphy.com/embed/5885nYOgBHdCw"));
    }
}
