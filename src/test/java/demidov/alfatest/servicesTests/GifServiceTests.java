package demidov.alfatest.servicesTests;

import demidov.alfatest.config.RequestParameters;
import demidov.alfatest.exceptions.CommonAppException;
import demidov.alfatest.services.GifService;
import demidov.alfatest.services.QueryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource("classpath:application-test.properties")
@ExtendWith({SpringExtension.class})
public class GifServiceTests {

    @Autowired
    private QueryService queryService;

    @Autowired
    private GifService gifService;

    @Autowired
    private RequestParameters requestParameters;

    @Test
    public void whenRequestParamNull_ReturnException() {
        requestParameters.setGiphyAppId(null);
        Exception thrown = Assertions.assertThrows(CommonAppException.class, () -> gifService.getRandomGifURL(requestParameters));

        Assertions.assertEquals("Request parameters for giphy service must not be null", thrown.getMessage());
    }

    @Test
    public void whenRequestParamIsBlank_ReturnException() {
        requestParameters.setGiphyAppId(" ");
        Exception thrown = Assertions.assertThrows(CommonAppException.class, () -> gifService.getRandomGifURL(requestParameters));

        Assertions.assertEquals("Request parameters for giphy service must not be null", thrown.getMessage());
    }

    @Test
    public void whenGifQueryParamsAreNull_thenException() {
        requestParameters.setRichQuery("");
        Exception thrown = Assertions.assertThrows(CommonAppException.class, () -> queryService.getGifQuery(requestParameters));

        Assertions.assertEquals("Query keywords parameters must not be null", thrown.getMessage());
    }
}
