package demidov.alfatest.services;

import demidov.alfatest.dto.ExchangeRateDTO;
import demidov.alfatest.feignclients.HistoricalExchangeClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import demidov.alfatest.feignclients.RecentExchangeRateClient;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ExchangeRateService {

    private final RecentExchangeRateClient recentExchangeRateClient;
    private final HistoricalExchangeClient historicalExchangeClient;
    private final String app_id;
    private final String symbols;

    public ExchangeRateService(RecentExchangeRateClient recentExchangeRateClient,
                               HistoricalExchangeClient historicalExchangeClient,
                               @Value("${exchangeAppId}") String app_id,
                               @Value("${comparingCurrency}") String symbols) {
        this.recentExchangeRateClient = recentExchangeRateClient;
        this.historicalExchangeClient = historicalExchangeClient;
        this.app_id = app_id;
        this.symbols = symbols;
    }

    public ExchangeRateDTO getRecentExchangeRate() {
        return recentExchangeRateClient.getExchangeRate(app_id, symbols);
    }

    public ExchangeRateDTO getHistoricalExchangeRate() {
        return historicalExchangeClient.getHistoricalExcRate(yesterdayDate(), app_id, symbols);
    }

    private String yesterdayDate() {
        OffsetDateTime todayInUTC = OffsetDateTime.now(ZoneOffset.UTC);
        LocalDate yesterdayInUTC = todayInUTC.minusDays(1).toLocalDate();
        return yesterdayInUTC.toString();
    }

    public static int compareRates(ExchangeRateDTO today, ExchangeRateDTO yesterday) {

        Map<String, Double> todayMap = today.getRates();
        Map<String, Double> yesterdayMap = yesterday.getRates();

        Optional<Double> optionalDouble1 = todayMap.values().stream().findFirst();
        Optional<Double> optionalDouble2 = yesterdayMap.values().stream().findFirst();

        if (optionalDouble1.isPresent() && optionalDouble2.isPresent()) {
            Double todayRate = optionalDouble1.get();
            Double yesterdayRate = optionalDouble2.get();

            if(todayRate >= yesterdayRate) return 1;
            else return -1;
        } else throw new NoSuchElementException("No rates found for requested currency");
    }
}
