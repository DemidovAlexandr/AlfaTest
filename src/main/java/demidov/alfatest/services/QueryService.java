package demidov.alfatest.services;

import demidov.alfatest.config.RequestParameters;
import demidov.alfatest.dto.ExchangeRateDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class QueryService {
    private final ExchangeRateService exchangeRateService;

    public QueryService(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    public String getGifQuery(RequestParameters requestParameters) {
        ExchangeRateDTO yesterdayExcDTO = exchangeRateService.getHistoricalExchangeRate(requestParameters);
        ExchangeRateDTO todayExcDTO = exchangeRateService.getRecentExchangeRate(requestParameters);

        int result = exchangeRateService.compareRates(todayExcDTO, yesterdayExcDTO);
        if (result > 0) return requestParameters.getRichQuery();
        else return requestParameters.getBrokeQuery();
    }
}
