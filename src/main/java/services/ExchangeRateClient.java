package services;

import dto.ExchangeRateDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "exchange",url = "https://openexchangerates.org/api/latest.json")
public interface ExchangeRateClient {

    @GetMapping
    ExchangeRateDTO getExchangeRate(@RequestParam String app_id, @RequestParam String symbols);
}
