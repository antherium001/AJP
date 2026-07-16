package classWork;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Q3_CreateStudents {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/demo_db";
        String username = "root";
        String password = "aaditya@123";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(url, username, password);
                 Statement stmt = conn.createStatement()) {

                String createTable = "CREATE TABLE IF NOT EXISTS Students ("
                        + "id INT PRIMARY KEY, "
                        + "name VARCHAR(100), "
                        + "age INT, "
                        + "grade CHAR(1))";
                stmt.executeUpdate(createTable);
                System.out.println("Table 'Students' created successfully with fields: id, name, age, grade");

                ResultSet rs = conn.getMetaData().getTables(null, null, "Students", null);
                if (rs.next()) {
                    System.out.println("Verified: Table 'Students' exists in the database.");
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    }
}
