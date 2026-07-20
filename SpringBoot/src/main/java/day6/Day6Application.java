package day6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "day6")
public class Day6Application {

    public static void main(String[] args) {
        SpringApplication.run(Day6Application.class, args);
    }
}
