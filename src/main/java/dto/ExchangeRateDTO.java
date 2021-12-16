package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRateDTO {

    String disclaimer;
    String licence;
    Integer timestamp;
    String base;
    ArrayList<Float> rates;
}
