package demidov.alfatest.controllers;

import demidov.alfatest.config.RequestParameters;
import demidov.alfatest.dto.ExchangeRateDTO;
import demidov.alfatest.services.ExchangeRateService;
import demidov.alfatest.services.GifService;
import demidov.alfatest.services.QueryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ExchangeController {

    private final ExchangeRateService exchangeRateService;
    private final QueryService queryService;
    private final GifService gifService;
    private final RequestParameters requestParameters;

    public ExchangeController(ExchangeRateService exchangeRateService, QueryService queryService,
                              GifService gifService, RequestParameters requestParameters) {
        this.exchangeRateService = exchangeRateService;
        this.queryService = queryService;
        this.gifService = gifService;
        this.requestParameters = requestParameters;
    }

    @GetMapping(path = "/gif")
    public String getGif(@RequestParam(value = "currency") String currency,
                                      Model model) {

        requestParameters.setComparingCurrency(currency);

        ExchangeRateDTO today = exchangeRateService.getRecentExchangeRate(requestParameters);
        ExchangeRateDTO yesterday = exchangeRateService.getHistoricalExchangeRate(requestParameters);
        String embedUrl = gifService.getRandomGifURL(requestParameters);

        model.addAttribute("currency", currency);
        model.addAttribute("status", queryService.getGifQuery(requestParameters));
        model.addAttribute("embedUrl", embedUrl);
        model.addAttribute("todayRate", today.getRates());
        model.addAttribute("yesterdayRate", yesterday.getRates());

        return "result";
    }

}
