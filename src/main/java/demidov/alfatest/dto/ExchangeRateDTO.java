package demidov.alfatest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Data
@NoArgsConstructor
public class ExchangeRateDTO{

    Integer timestamp;
    String base;
    Map<String, Double> rates;
}
