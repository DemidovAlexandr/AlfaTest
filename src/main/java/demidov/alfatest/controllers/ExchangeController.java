package demidov.alfatest.controllers;

import demidov.alfatest.dto.ExchangeRateDTO;
import demidov.alfatest.services.ExchangeRateService;
import demidov.alfatest.services.GifService;
import demidov.alfatest.services.QueryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
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

    @GetMapping(path = "/gif")
    public String getGiphyRawResponse(Model model) {

        ExchangeRateDTO today = exchangeRateService.getRecentExchangeRate();
        ExchangeRateDTO yesterday = exchangeRateService.getHistoricalExchangeRate();
        String embedUrl = gifService.getRandomGifURL();

        model.addAttribute("embedUrl", embedUrl);
        model.addAttribute("todayRate", today.getRates());
        model.addAttribute("yesterdayRate", yesterday.getRates());

        return "result";
    }

}
