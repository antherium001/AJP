package day5.q47;

public class UserService {

    private final DataSource dataSource;

    public UserService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void displayInfo() {
        System.out.println("UserService connected to: " + dataSource);
    }
}
