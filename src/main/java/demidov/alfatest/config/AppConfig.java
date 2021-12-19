package demidov.alfatest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

@Configuration
public class AppConfig {

    @Bean
    @Description("Provides default application settings from app.properties file")
    public RequestParameters requestParameters() {
        return new RequestParameters();
    }
}
