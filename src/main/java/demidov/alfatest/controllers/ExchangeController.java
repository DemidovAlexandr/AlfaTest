package demidov.alfatest.controllers;

import demidov.alfatest.dto.ExchangeRateDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import demidov.alfatest.services.ExchangeRateService;

@RestController
public class ExchangeController {

    private final ExchangeRateService exchangeRateService;

    public ExchangeController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping(path = "/exchange")
    public @ResponseBody
    ExchangeRateDTO get() {return exchangeRateService.getExchangeRate();}


}
