package day5.q49;

import org.springframework.stereotype.Component;

@Component
public class DataProcessor {

    public String process() {
        return "Processed data from DataProcessor using @Component";
    }
}
