package day5.q48;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("day5.q48")
class AppConfig {
}

public class App {

    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(AppConfig.class);

        System.out.println("=== Q48 - Component Scanning with @Component, @Service, @Repository ===");

        UserService service = context.getBean(UserService.class);
        service.listAllUsers();

        UserValidator validator = context.getBean(UserValidator.class);
        System.out.println("\nValidate 'Amit': " + validator.validate("Amit"));
        System.out.println("Validate '': " + validator.validate(""));

        System.out.println("\nBeans registered by component scanning:");
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println("  - " + name);
        }

        context.close();
    }
}
