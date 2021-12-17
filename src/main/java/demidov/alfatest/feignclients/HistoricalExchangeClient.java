package demidov.alfatest.feignclients;

import demidov.alfatest.dto.ExchangeRateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "historical", url = "${exchangeHistoryURL}")
public interface HistoricalExchangeClient {

    @GetMapping(value = "/{date}.json")
    ExchangeRateDTO getHistoricalExcRate(@PathVariable("date") String date, @RequestParam String app_id, @RequestParam String symbols);
}
