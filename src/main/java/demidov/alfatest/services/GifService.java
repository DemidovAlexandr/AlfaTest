package demidov.alfatest.services;

import demidov.alfatest.config.RequestParameters;
import demidov.alfatest.dto.GifResponseDTO;
import demidov.alfatest.feignclients.GifClient;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GifService {

    private final GifClient gifClient;
    private final QueryService queryService;

    public GifService(GifClient gifClient, QueryService queryService) {
        this.gifClient = gifClient;
        this.queryService = queryService;
    }

    private GifResponseDTO getGifResponseDTO(RequestParameters requestParameters) {
        String query = queryService.getGifQuery(requestParameters);
        return gifClient.getGif(requestParameters.getGiphyAppId(), query, requestParameters.getGiphyRating());
    }

    public String getRandomGifURL(RequestParameters requestParameters) {
        GifResponseDTO responseDTO = getGifResponseDTO(requestParameters);
        Random random = new Random();
        int index = random.nextInt(50);

        return responseDTO.getOriginalURL().get(index);
    }

}
