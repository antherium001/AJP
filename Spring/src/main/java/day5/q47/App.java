package day5.q47;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AppConfig {

    @Bean
    public DataSource dataSource() {
        return new DataSource("jdbc:mysql://localhost:3306/demo_db", "root");
    }

    @Bean
    public UserService userService() {
        return new UserService(dataSource());
    }
}

public class App {

    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(AppConfig.class);

        System.out.println("=== Q47 - Java-based Configuration with @Configuration and @Bean ===");

        DataSource ds = context.getBean(DataSource.class);
        System.out.println("DataSource bean: " + ds);

        UserService us = context.getBean(UserService.class);
        us.displayInfo();

        context.close();
    }
}
