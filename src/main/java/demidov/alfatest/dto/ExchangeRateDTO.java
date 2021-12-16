package demidov.alfatest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRateDTO {

//    String disclaimer;
//    String licence;
    Integer timestamp;
    String base;
    Map<String, Double> rates;
}
