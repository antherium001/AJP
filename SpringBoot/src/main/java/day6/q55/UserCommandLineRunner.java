package day6.q55;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component("userCommandLineRunner")
public class UserCommandLineRunner implements CommandLineRunner {

    @org.springframework.beans.factory.annotation.Qualifier("inMemoryUserService")
    private final UserService userService;

    public UserCommandLineRunner(@org.springframework.beans.factory.annotation.Qualifier("inMemoryUserService") UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        System.out.println("=== Q55 - User Service with Constructor Injection ===");

        System.out.println("\n--- Creating Users ---");
        userService.createUser("Amit Verma", "amit@gmail.com");
        userService.createUser("Priya Sharma", "priya@gmail.com");
        userService.createUser("Rahul Singh", "rahul@gmail.com");

        System.out.println("\n--- All Users ---");
        userService.getAllUsers().forEach(System.out::println);

        System.out.println("\n--- Find User by ID=2 ---");
        System.out.println(userService.getUserById(2));

        System.out.println("\n--- Delete User ID=1 ---");
        userService.deleteUser(1);

        System.out.println("\n--- All Users After Deletion ---");
        userService.getAllUsers().forEach(System.out::println);
    }
}
