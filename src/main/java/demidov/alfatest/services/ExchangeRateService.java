package demidov.alfatest.services;

import demidov.alfatest.config.RequestParameters;
import demidov.alfatest.dto.ExchangeRateDTO;
import demidov.alfatest.exceptions.CommonAppException;
import demidov.alfatest.feignclients.HistoricalExchangeClient;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import demidov.alfatest.feignclients.RecentExchangeRateClient;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.Optional;

@Service
public class ExchangeRateService {

    private final RecentExchangeRateClient recentExchangeRateClient;
    private final HistoricalExchangeClient historicalExchangeClient;

    @Getter
    @Setter
    private ExchangeRateDTO todayDTO;

    @Getter
    @Setter
    private ExchangeRateDTO yesterdayDTO;

    @Getter
    private String currency;

    @Getter
    private String app_id;

    public ExchangeRateService(RecentExchangeRateClient recentExchangeRateClient,
                               HistoricalExchangeClient historicalExchangeClient) {
        this.recentExchangeRateClient = recentExchangeRateClient;
        this.historicalExchangeClient = historicalExchangeClient;
    }

    public ExchangeRateDTO getRecentExchangeRate(RequestParameters requestParameters) {

        app_id = requestParameters.getExchangeAppId();
        currency = requestParameters.getComparingCurrency();

        if (app_id == null || currency == null || app_id.trim().isEmpty() || currency.trim().isEmpty())
            throw new CommonAppException("Request parameters for exchange service must not be null");

        return recentExchangeRateClient.getExchangeRate(app_id, currency);
    }

    public ExchangeRateDTO getHistoricalExchangeRate(RequestParameters requestParameters) {

        app_id = requestParameters.getExchangeAppId();
        currency = requestParameters.getComparingCurrency();

        if (app_id == null || currency == null || app_id.trim().isEmpty() || currency.trim().isEmpty())
            throw new CommonAppException("Request parameters for exchange service must not be null");

        return historicalExchangeClient.getHistoricalExcRate(yesterdayDate(), app_id, currency);
    }

    private String yesterdayDate() {
        OffsetDateTime todayInUTC = OffsetDateTime.now(ZoneOffset.UTC);
        LocalDate yesterdayInUTC = todayInUTC.minusDays(1).toLocalDate();
        return yesterdayInUTC.toString();
    }

    public int compareRates(ExchangeRateDTO today, ExchangeRateDTO yesterday) {

        if (today == null || yesterday == null)
            throw new CommonAppException("ExchangeRateDTO must not be null");

        saveRates(today, yesterday);

        Map<String, Double> todayMap = todayDTO.getRates();
        Map<String, Double> yesterdayMap = yesterdayDTO.getRates();

        Optional<Double> optionalDouble1 = todayMap.values().stream().findFirst();
        Optional<Double> optionalDouble2 = yesterdayMap.values().stream().findFirst();

        if (optionalDouble1.isPresent() && optionalDouble2.isPresent()) {
            Double todayRate = optionalDouble1.get();
            Double yesterdayRate = optionalDouble2.get();

            if (todayRate >= yesterdayRate) return 1;
            else return -1;
        } else throw new CommonAppException("No rates found for requested currency");
    }

    public void saveRates(ExchangeRateDTO today, ExchangeRateDTO yesterday) {
        setTodayDTO(today);
        setYesterdayDTO(yesterday);
    }
}
