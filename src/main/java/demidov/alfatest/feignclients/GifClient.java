package demidov.alfatest.feignclients;

import demidov.alfatest.dto.GifResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "gifClient", url = "${giphyServiceURL}")
public interface GifClient {

    @GetMapping
    GifResponseDTO getGif(@RequestParam String api_key, @RequestParam String q, @RequestParam String rating);
}
