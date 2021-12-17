package demidov.alfatest.services;

import demidov.alfatest.dto.GifDTO;
import demidov.alfatest.dto.GifResponseDTO;
import demidov.alfatest.feignclients.GifClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GifService {

    private final GifClient gifClient;
    private final QueryService queryService;
    private final String api_key;
    private final String rating;

    public GifService(GifClient gifClient, QueryService queryService, @Value("${giphyAppId}") String api_key, @Value("${giphyRating}") String rating) {
        this.gifClient = gifClient;
        this.queryService = queryService;
        this.api_key = api_key;
        this.rating = rating;
    }

    private GifResponseDTO getGifResponseDTO() {
        String query = queryService.getGifQuery();
        return gifClient.getGif(api_key, query, rating);
    }

    public String getRandomGifURL() {
        GifResponseDTO responseDTO = getGifResponseDTO();
        Random random = new Random(47);
        int index = random.nextInt(50);

        return responseDTO.getOriginalURL().get(index);
    }

}
