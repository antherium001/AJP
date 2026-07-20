package day6.q56;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(int id) {
        return userRepository.findById(id);
    }

    public void createUser(String name, String email) {
        User user = new User(0, name, email);
        userRepository.save(user);
        System.out.println("  Created: " + user);
    }

    public void updateUser(int id, String name, String email) {
        User user = new User(id, name, email);
        userRepository.update(user);
        System.out.println("  Updated: " + user);
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
        System.out.println("  Deleted user with id=" + id);
    }

    public int getUserCount() {
        return userRepository.count();
    }
}
