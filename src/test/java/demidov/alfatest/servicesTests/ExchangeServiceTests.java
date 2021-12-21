package demidov.alfatest.servicesTests;

import demidov.alfatest.config.RequestParameters;
import demidov.alfatest.dto.ExchangeRateDTO;
import demidov.alfatest.exceptions.CommonAppException;
import demidov.alfatest.services.ExchangeRateService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource("classpath:application-test.properties")
@ExtendWith({SpringExtension.class})
public class ExchangeServiceTests {

    @Autowired
    private ExchangeRateService exchangeRateService;

    @MockBean
    private RequestParameters requestParameters;

    @Test
    public void whenRequestParamNull_exchangeReturnException() {
        requestParameters.setComparingCurrency(null);
        Exception thrown = Assertions.assertThrows(CommonAppException.class, () -> exchangeRateService.getRecentExchangeRate(requestParameters));

        Assertions.assertEquals("Request parameters for exchange service must not be null", thrown.getMessage());
    }

    @Test
    public void whenRequestParamNull_historicalExchangeReturnException() {
        requestParameters.setComparingCurrency(null);
        Exception thrown = Assertions.assertThrows(CommonAppException.class, () -> exchangeRateService.getHistoricalExchangeRate(requestParameters));

        Assertions.assertEquals("Request parameters for exchange service must not be null", thrown.getMessage());
    }

    @Test
    public void whenRequestParamIsBlank_exchangeReturnException() {
        requestParameters.setComparingCurrency(" ");
        Exception thrown = Assertions.assertThrows(CommonAppException.class, () -> exchangeRateService.getRecentExchangeRate(requestParameters));

        Assertions.assertEquals("Request parameters for exchange service must not be null", thrown.getMessage());
    }

    @Test
    public void whenRequestParamIsBlank_historicalExchangeReturnException() {
        requestParameters.setComparingCurrency(" ");
        Exception thrown = Assertions.assertThrows(CommonAppException.class, () -> exchangeRateService.getHistoricalExchangeRate(requestParameters));

        Assertions.assertEquals("Request parameters for exchange service must not be null", thrown.getMessage());
    }

    @Test
    public void getYesterdayDateWorksCorrectly() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        OffsetDateTime todayInUTC = OffsetDateTime.now(ZoneOffset.UTC);
        LocalDate yesterdayInUTC = todayInUTC.minusDays(1).toLocalDate();
        Method method = exchangeRateService.getClass().getDeclaredMethod("yesterdayDate");
        method.setAccessible(true);

        Assertions.assertEquals(yesterdayInUTC.toString(),method.invoke(exchangeRateService));
    }

    @Test
    public void whenNonExistingCurrencyCode_thenException() {
        ExchangeRateDTO today = new ExchangeRateDTO();
        ExchangeRateDTO yesterday = new ExchangeRateDTO();
        today.setRates(new HashMap<>());
        yesterday.setRates(new HashMap<>());

        Exception thrown = Assertions.assertThrows(CommonAppException.class, () -> exchangeRateService.compareRates(today, yesterday));

        Assertions.assertEquals("No rates found for requested currency", thrown.getMessage());
    }

    @Test
    public void whenNullDTO_thenException() {
        ExchangeRateDTO today = new ExchangeRateDTO();
        ExchangeRateDTO yesterday = null;
        today.setRates(new HashMap<>());
        Exception thrown = Assertions.assertThrows(CommonAppException.class, () -> exchangeRateService.compareRates(today, yesterday));

        Assertions.assertEquals("ExchangeRateDTO must not be null", thrown.getMessage());
    }
}
