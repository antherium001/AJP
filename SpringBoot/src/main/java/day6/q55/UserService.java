package day6.q55;

import org.springframework.stereotype.Service;
import java.util.List;

@Service("inMemoryUserService")
public class UserService {

    private final UserRepository userRepository;

    public UserService(@org.springframework.beans.factory.annotation.Qualifier("inMemoryUserRepo") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String name, String email) {
        User user = new User(0, name, email);
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public boolean deleteUser(int id) {
        return userRepository.deleteById(id);
    }
}
