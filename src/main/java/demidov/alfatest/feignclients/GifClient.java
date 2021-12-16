package demidov.alfatest.feignclients;

import demidov.alfatest.dto.GifDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "gifClient", url = "${giphyServiceURL}")
public interface GifClient {

    @GetMapping
    GifDTO getGif(@RequestParam String app_id, @RequestParam String q, @RequestParam String rating);
}
