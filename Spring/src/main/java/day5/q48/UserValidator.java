package day5.q48;

import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    public boolean validate(String name) {
        return name != null && !name.trim().isEmpty();
    }
}
