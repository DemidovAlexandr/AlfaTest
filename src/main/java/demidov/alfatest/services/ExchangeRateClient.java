package demidov.alfatest.services;

import demidov.alfatest.dto.ExchangeRateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@PropertySource("classpath:application.properties")
@FeignClient(name = "exchange",url = "${exchangeServiceURL}")
public interface ExchangeRateClient {

    @GetMapping
    ExchangeRateDTO getExchangeRate(@RequestParam String app_id, @RequestParam String symbols);
}
