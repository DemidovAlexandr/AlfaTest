package demidov.alfatest.controllers;

import demidov.alfatest.dto.ExchangeRateDTO;
import demidov.alfatest.services.QueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import demidov.alfatest.services.ExchangeRateService;

@RestController
public class ExchangeController {

    private final ExchangeRateService exchangeRateService;
    private final QueryService queryService;

    public ExchangeController(ExchangeRateService exchangeRateService, QueryService queryService) {
        this.exchangeRateService = exchangeRateService;
        this.queryService = queryService;
    }

    @GetMapping(path = "/query")
    public @ResponseBody
    String get() {return queryService.getGifQuery();}

    @GetMapping(path = "/history")
    public @ResponseBody
    ExchangeRateDTO getHistory() {return exchangeRateService.getHistoricalExchangeRate();}

    @GetMapping(path = "/recent")
    public @ResponseBody
    ExchangeRateDTO getRecent() {return exchangeRateService.getRecentExchangeRate();}
}
