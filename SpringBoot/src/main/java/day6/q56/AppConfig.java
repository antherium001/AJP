package day6.q56;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;

@Configuration
@ComponentScan("day6.q56")
class AppConfig {

    @Bean
    public String applicationName() {
        return "Q56 - Full User Profile Application";
    }
}
