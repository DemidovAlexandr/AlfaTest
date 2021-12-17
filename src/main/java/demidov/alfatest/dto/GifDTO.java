package demidov.alfatest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GifDTO {

    @JsonProperty("embed_url")
    private String url;
}
