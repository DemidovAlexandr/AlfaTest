package demidov.alfatest.services;

import demidov.alfatest.dto.ExchangeRateDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class QueryService {
    private final ExchangeRateService exchangeRateService;

    private final String richQuery;
    private final String brokeQuery;


    public QueryService(ExchangeRateService exchangeRateService,
                        @Value("${richQuery}") String richQuery,
                        @Value("${brokeQuery}") String brokeQuery) {
        this.exchangeRateService = exchangeRateService;
        this.richQuery = richQuery;
        this.brokeQuery = brokeQuery;
    }

    public String getGifQuery() {
        ExchangeRateDTO yesterdayExcDTO = exchangeRateService.getHistoricalExchangeRate();
        ExchangeRateDTO todayExcDTO = exchangeRateService.getRecentExchangeRate();

        int result = ExchangeRateService.compareRates(todayExcDTO, yesterdayExcDTO);
        if (result > 0) return richQuery;
        else return brokeQuery;
    }
}
