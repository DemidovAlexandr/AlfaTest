package demidov.alfatest.clientsTests;

import com.github.tomakehurst.wiremock.WireMockServer;
import demidov.alfatest.feignclients.HistoricalExchangeClient;
import demidov.alfatest.feignclients.RecentExchangeRateClient;
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
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@ContextConfiguration(classes = {WireMockConfig.class})
@TestPropertySource("classpath:application-test.properties")
public class RecentExchangeRateClientTest {

    @Autowired
    private WireMockServer mockExchangeServer;

    @Autowired
    private RecentExchangeRateClient recentExchangeRateClient;

    @Value("${exchangeAppId}")
    private String app_id;

    @Value("${comparingCurrency}")
    private String symbols;


    @BeforeEach
    void setUp() throws IOException {
        ClientsMocks.setupRecentExchangeResponse(mockExchangeServer);
    }

    @Test
    public void whenGet_thenRatesShouldBeReturned() {
        assertFalse(recentExchangeRateClient.getExchangeRate(app_id, symbols).getRates().isEmpty());
    }

    @Test
    public void whenGet_thenCorrectRatesShouldBeReturned() {
        assertTrue(recentExchangeRateClient.getExchangeRate(app_id, symbols).getRates().containsKey("RUB"));
        assertTrue(recentExchangeRateClient.getExchangeRate(app_id, symbols).getRates().containsValue(74.204));
    }
}
