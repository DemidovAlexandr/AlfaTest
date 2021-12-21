package demidov.alfatest.clientsTests;

import com.github.tomakehurst.wiremock.WireMockServer;
import demidov.alfatest.feignclients.HistoricalExchangeClient;
import demidov.alfatest.testconfig.ClientsMocks;
import demidov.alfatest.testconfig.WireMockConfig;
import org.junit.jupiter.api.BeforeAll;
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
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@ContextConfiguration(classes = {WireMockConfig.class})
@TestPropertySource("classpath:application-test.properties")
public class HistoricalExchangeRateClientTest {

    @Autowired
    private WireMockServer mockExchangeServer;

    @Autowired
    private HistoricalExchangeClient historicalExchangeClient;

    @Value("${exchangeAppId}")
    private String app_id;

    @Value("${comparingCurrency}")
    private String symbols;

    private static String validDate;

    @BeforeAll
    static void getDate() {
        OffsetDateTime todayInUTC = OffsetDateTime.now(ZoneOffset.UTC);
        LocalDate yesterdayInUTC = todayInUTC.minusDays(1).toLocalDate();
        validDate = yesterdayInUTC.toString();
    }

    @BeforeEach
    void setUp() throws IOException {
        ClientsMocks.setupHistoricalExchangeResponse(mockExchangeServer);
    }

    @Test
    public void whenGetHistorical_thenRatesShouldBeReturned() {
        assertFalse(historicalExchangeClient.getHistoricalExcRate(validDate, app_id, symbols).getRates().isEmpty());

    }

    @Test
    public void whenGet_thenCorrectRatesShouldBeReturned() {
        assertTrue(historicalExchangeClient.getHistoricalExcRate(validDate, app_id, symbols).getRates().containsKey("RUB"));
        assertTrue(historicalExchangeClient.getHistoricalExcRate(validDate, app_id, symbols).getRates().containsValue(74.117339));
    }
}
