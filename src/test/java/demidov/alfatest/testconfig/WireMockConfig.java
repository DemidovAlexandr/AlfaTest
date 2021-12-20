package demidov.alfatest.testconfig;


import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.context.annotation.Bean;

@org.springframework.boot.test.context.TestConfiguration
public class WireMockConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public WireMockServer mockExchangeService() {
        return new WireMockServer(8081);
    }
}
