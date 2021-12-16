package demidov.alfatest.servicesImpl;

import demidov.alfatest.dto.ExchangeRateDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import demidov.alfatest.services.ExchangeRateClient;

@Service
public class ExchangeRateService {

    private final ExchangeRateClient client;
    private final String app_id;
    private final String symbols;

    public ExchangeRateService(ExchangeRateClient client, @Value("${exchangeAppId}") String app_id,
                               @Value("${comparingCurrency}") String symbols) {
        this.client = client;
        this.app_id = app_id;
        this.symbols = symbols;
    }

    public ExchangeRateDTO getExchangeRate() {
        return client.getExchangeRate(app_id, symbols);
    }
}
