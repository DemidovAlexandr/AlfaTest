package controllers;

import dto.ExchangeRateDTO;
import dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import servicesImpl.ExchangeRateService;

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
