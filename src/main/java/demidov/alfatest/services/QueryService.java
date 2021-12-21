package demidov.alfatest.services;

import demidov.alfatest.config.RequestParameters;
import demidov.alfatest.dto.ExchangeRateDTO;
import demidov.alfatest.exceptions.CommonAppException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
public class QueryService {
    private final ExchangeRateService exchangeRateService;

    @Getter
    private String richQuery;

    @Getter
    private String brokeQuery;

    public QueryService(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    public String getGifQuery(RequestParameters requestParameters) {

        richQuery = requestParameters.getRichQuery();
        brokeQuery = requestParameters.getBrokeQuery();
        if (richQuery == null || brokeQuery == null || richQuery.trim().isEmpty() || brokeQuery.trim().isEmpty())
            throw new CommonAppException("Query keywords parameters must not be null");

        ExchangeRateDTO yesterdayExcDTO = exchangeRateService.getHistoricalExchangeRate(requestParameters);
        ExchangeRateDTO todayExcDTO = exchangeRateService.getRecentExchangeRate(requestParameters);

        int result = exchangeRateService.compareRates(todayExcDTO, yesterdayExcDTO);
        if (result > 0) return richQuery;
        else return brokeQuery;
    }
}
