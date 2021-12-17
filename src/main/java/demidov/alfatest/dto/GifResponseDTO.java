package demidov.alfatest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class GifResponseDTO {

    @JsonIgnore
    private List<String> originalURL = new ArrayList<>();

    @SuppressWarnings("unchecked")
    @JsonProperty("data")
    private void unpackGif(List<Object> data) {

        for (Object obj : data
             ) {
            Map<String, Object> datamap = (Map<String, Object>) obj;
            Map<String, Object> images = (Map<String, Object>) datamap.get("images");
            Map<String, String> original = (Map<String, String>) images.get("original");
            originalURL.add(original.get("url"));
        }
    }
}
