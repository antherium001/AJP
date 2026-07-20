package day5.q46;

import org.springframework.stereotype.Component;

@Component
public class MessageService {

    public String getMessage() {
        return "Hello from Spring Framework! Field Injection with @Autowired demo.";
    }
}
