package demidov.alfatest.services;

import demidov.alfatest.feignclients.GifClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GifService {

    private final GifClient gifClient;
    private final String app_id;
    private final String query;
    private final String rating;

    public GifService(GifClient gifClient, @Value("${giphyAppId}") String app_id, String query, @Value("${giphyRating}") String rating) {
        this.gifClient = gifClient;
        this.app_id = app_id;
        this.query = query;
        this.rating = rating;
    }
}
