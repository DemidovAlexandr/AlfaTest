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

        try {
            richQuery = requestParameters.getRichQuery();
            brokeQuery = requestParameters.getBrokeQuery();
            if (richQuery.isBlank() || brokeQuery.isBlank()) throw new CommonAppException("Query keywords parameters must not be empty");
        } catch (NullPointerException e) {
            throw new CommonAppException("Query keywords parameters must not be null");
        }

        ExchangeRateDTO yesterdayExcDTO = exchangeRateService.getHistoricalExchangeRate(requestParameters);
        ExchangeRateDTO todayExcDTO = exchangeRateService.getRecentExchangeRate(requestParameters);

        int result = exchangeRateService.compareRates(todayExcDTO, yesterdayExcDTO);
        if (result > 0) return richQuery;
        else return brokeQuery;
    }
}
