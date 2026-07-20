package day5.q49;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("day5.q49")
class AppConfig {

    @Bean
    public String appVersion() {
        return "1.0.0";
    }
}

public class App {

    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(AppConfig.class);

        System.out.println("=== Q49 - Full Annotation-based Configuration ===");

        ConfigRepo repo = context.getBean(ConfigRepo.class);
        System.out.println(repo.fetchData());

        NotificationService notificationService = context.getBean(NotificationService.class);
        notificationService.send();

        String version = context.getBean("appVersion", String.class);
        System.out.println("\n@Bean defined appVersion: " + version);

        System.out.println("\nAll beans in context:");
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println("  - " + name);
        }

        context.close();
    }
}
