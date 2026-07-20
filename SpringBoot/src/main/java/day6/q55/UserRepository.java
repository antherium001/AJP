package day6.q55;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("inMemoryUserRepo")
public class UserRepository {

    private final List<User> users = new ArrayList<>();
    private int nextId = 1;

    public User save(User user) {
        user.setId(nextId++);
        users.add(user);
        return user;
    }

    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    public Optional<User> findById(int id) {
        return users.stream().filter(u -> u.getId() == id).findFirst();
    }

    public boolean deleteById(int id) {
        return users.removeIf(u -> u.getId() == id);
    }
}
