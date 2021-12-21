package demidov.alfatest.services;

import demidov.alfatest.config.RequestParameters;
import demidov.alfatest.dto.GifResponseDTO;
import demidov.alfatest.exceptions.CommonAppException;
import demidov.alfatest.feignclients.GifClient;
import lombok.Getter;
import lombok.Setter;
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

    @Getter
    @Setter
    private GifResponseDTO responseDTO;

    public GifService(GifClient gifClient, QueryService queryService) {
        this.gifClient = gifClient;
        this.queryService = queryService;
    }

    public String getRandomGifURL(RequestParameters requestParameters) {
        responseDTO = getGifResponseDTO(requestParameters);
        return getGifURL(responseDTO);
    }

    private GifResponseDTO getGifResponseDTO(RequestParameters requestParameters) {
        app_id = requestParameters.getGiphyAppId();
        rating = requestParameters.getGiphyRating();

        if (app_id == null || rating == null || app_id.trim().isEmpty() || rating.trim().isEmpty())
            throw new CommonAppException("Request parameters for giphy service must not be null");

        String query = queryService.getGifQuery(requestParameters);

        return gifClient.getGif(app_id, query, rating);
    }

    private String getGifURL(GifResponseDTO responseDTO) {
        if (responseDTO == null) throw new CommonAppException("DTO must not be null");

        List<String> gifURLList = responseDTO.getOriginalURL();
        if (gifURLList.isEmpty()) throw new CommonAppException("No gifs were found for this query");

        Random random = new Random();
        int index = random.nextInt(gifURLList.size());

        return responseDTO.getOriginalURL().get(index);
    }
}
