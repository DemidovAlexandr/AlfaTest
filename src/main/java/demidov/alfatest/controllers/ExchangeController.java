package demidov.alfatest.controllers;

import demidov.alfatest.dto.ExchangeRateDTO;
import demidov.alfatest.dto.GifDTO;
import demidov.alfatest.dto.GifResponseDTO;
import demidov.alfatest.services.GifService;
import demidov.alfatest.services.QueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import demidov.alfatest.services.ExchangeRateService;

import java.util.List;

@RestController
public class ExchangeController {

    private final ExchangeRateService exchangeRateService;
    private final QueryService queryService;
    private final GifService gifService;

    public ExchangeController(ExchangeRateService exchangeRateService, QueryService queryService,
                              GifService gifService) {
        this.exchangeRateService = exchangeRateService;
        this.queryService = queryService;
        this.gifService = gifService;
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

    @GetMapping(path = "/gifs", produces = "image/gif")
    public @ResponseBody
    String getGiphyRawResponse() {return gifService.getRandomGifURL();}


}
