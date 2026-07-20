package day5.q46;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class App {

    @Autowired
    private MessageService messageService;

    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(AppConfig.class);
        App app = context.getBean(App.class);
        System.out.println("=== Q46 - Field Injection with @Autowired ===");
        System.out.println(app.messageService.getMessage());
        context.close();
    }
}
