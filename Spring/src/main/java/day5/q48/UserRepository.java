package day5.q48;

import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class UserRepository {

    public List<String> findAll() {
        return List.of("Amit Verma", "Priya Sharma", "Rahul Singh", "Neha Gupta");
    }
}
