package demidov.alfatest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class GifDTO {

    private String gifOriginalURL;

    @SuppressWarnings("unchecked")
    @JsonProperty("data")
    private void unpackGif(Map<String, Object> data) {
        Map<String, Object> images = (Map<String, Object>) data.get("images");
        Map<String, String> original = (Map<String, String>) images.get("original");
        this.gifOriginalURL = original.get("url");
    }

  //  api.giphy.com/v1/gifs/search?api_key=460LCWHTb1C4B48SAkZySSIHHq6Is5fw&q=rich&limit=1


    public String getGifOriginalURL() {
        return gifOriginalURL;
    }

    public void setGifOriginalURL(String gifOriginalURL) {
        this.gifOriginalURL = gifOriginalURL;
    }
}
