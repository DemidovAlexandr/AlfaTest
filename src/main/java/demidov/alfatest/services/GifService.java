package demidov.alfatest.services;

import demidov.alfatest.feignclients.GifClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GifService {

    private final GifClient gifClient;
    private final QueryService queryService;
    private final String app_id;
    private final String rating;

    public GifService(GifClient gifClient, QueryService queryService, @Value("${giphyAppId}") String app_id, @Value("${giphyRating}") String rating) {
        this.gifClient = gifClient;
        this.queryService = queryService;
        this.app_id = app_id;
        this.rating = rating;
    }
}
