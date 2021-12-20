package demidov.alfatest.services;

import demidov.alfatest.config.RequestParameters;
import demidov.alfatest.dto.GifResponseDTO;
import demidov.alfatest.exceptions.CommonAppException;
import demidov.alfatest.feignclients.GifClient;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class GifService {

    private final GifClient gifClient;
    private final QueryService queryService;

    @Getter
    private String app_id;

    @Getter
    private String rating;

    public GifService(GifClient gifClient, QueryService queryService) {
        this.gifClient = gifClient;
        this.queryService = queryService;
    }

    private GifResponseDTO getGifResponseDTO(RequestParameters requestParameters) {
        String query = queryService.getGifQuery(requestParameters);

        try {
            app_id = requestParameters.getGiphyAppId();
            rating = requestParameters.getGiphyRating();
        } catch (NullPointerException e) {
            throw new CommonAppException("Request parameters for giphy service must not be null");
        }

        return gifClient.getGif(app_id, query, rating);
    }

    public String getRandomGifURL(RequestParameters requestParameters) {
        GifResponseDTO responseDTO = getGifResponseDTO(requestParameters);

        List<String> gifURLList = responseDTO.getOriginalURL();
        if (gifURLList.isEmpty()) throw new CommonAppException("No gifs were found for this query");

        Random random = new Random();
        int index = random.nextInt(gifURLList.size());

        return responseDTO.getOriginalURL().get(index);
    }

}
