package demidov.alfatest.feignclients;

import demidov.alfatest.dto.ExchangeRateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "exchangeClient",url = "${exchangeServiceURL}")
public interface ExchangeRateClient {

    @GetMapping
    ExchangeRateDTO getExchangeRate(@RequestParam String app_id, @RequestParam String symbols);
}
