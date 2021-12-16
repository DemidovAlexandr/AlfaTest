package servicesImpl;

import dto.ExchangeRateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import services.ExchangeRateClient;

@Service
@PropertySource("classpath:application.properties")
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
