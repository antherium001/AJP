package day6.q56;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;

    public DataInitializer(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        System.out.println("=== Q56 - Full User Profile App (JDBC + H2) ===");

        System.out.println("\n--- Users from data.sql (pre-loaded) ---");
        userService.getAllUsers().forEach(System.out::println);
        System.out.println("Total users: " + userService.getUserCount());

        System.out.println("\n--- Creating new user ---");
        userService.createUser("Neha Gupta", "neha@gmail.com");

        System.out.println("\n--- Updating user id=1 ---");
        userService.updateUser(1, "Amit K. Verma", "amit.verma@gmail.com");

        System.out.println("\n--- All Users After CRUD ---");
        userService.getAllUsers().forEach(System.out::println);
        System.out.println("Total users: " + userService.getUserCount());

        System.out.println("\n--- Deleting user id=2 ---");
        userService.deleteUser(2);

        System.out.println("\n--- Final User List ---");
        userService.getAllUsers().forEach(System.out::println);
        System.out.println("Total users: " + userService.getUserCount());
    }
}
