package demidov.alfatest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Data
@NoArgsConstructor
public class ExchangeRateDTO{

    private Integer timestamp;
    private String base;
    private Map<String, Double> rates;
}
