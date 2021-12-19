package demidov.alfatest.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class RequestParameters {
    @Value("${comparingCurrency}")
    private String comparingCurrency;

    @Value("${exchangeServiceURL}")
    private String exchangeServiceURL;

    @Value("${exchangeHistoryURL}")
    private String exchangeHistoryURL;

    @Value("${exchangeAppId}")
    private String exchangeAppId;

    @Value("${giphyServiceURL}")
    private String giphyServiceURL;

    @Value("${giphyAppId}")
    private String giphyAppId;

    @Value("${giphyRating}")
    private String giphyRating;

    @Value("${richQuery}")
    private String richQuery;

    @Value("${brokeQuery}")
    private String brokeQuery;
}
