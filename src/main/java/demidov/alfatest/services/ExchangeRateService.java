package demidov.alfatest.services;

import demidov.alfatest.dto.ExchangeRateDTO;
import demidov.alfatest.feignclients.HistoricalExchangeClient;
import lombok.Getter;
import lombok.Setter;
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

    @Getter
    @Setter
    private ExchangeRateDTO todayDTO;

    @Getter
    @Setter
    private ExchangeRateDTO yesterdayDTO;

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

    public int compareRates(ExchangeRateDTO today, ExchangeRateDTO yesterday) {
        saveRates(today, yesterday);

        Map<String, Double> todayMap = todayDTO.getRates();
        Map<String, Double> yesterdayMap = yesterdayDTO.getRates();

        Optional<Double> optionalDouble1 = Optional.ofNullable(todayMap.get(symbols));
        Optional<Double> optionalDouble2 = Optional.ofNullable(yesterdayMap.get(symbols));

        if (optionalDouble1.isPresent() && optionalDouble2.isPresent()) {
            Double todayRate = optionalDouble1.get();
            Double yesterdayRate = optionalDouble2.get();

            if(todayRate >= yesterdayRate) return 1;
            else return -1;
        } else throw new NoSuchElementException("No rates found for requested currency");
    }

    public void saveRates(ExchangeRateDTO today, ExchangeRateDTO yesterday) {
        setTodayDTO(today);
        setYesterdayDTO(yesterday);
    }
}
