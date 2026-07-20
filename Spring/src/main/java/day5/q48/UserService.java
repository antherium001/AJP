package day5.q48;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void listAllUsers() {
        List<String> users = userRepository.findAll();
        System.out.println("All Users:");
        for (String user : users) {
            System.out.println("  - " + user);
        }
    }
}
