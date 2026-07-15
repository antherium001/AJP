package classWork;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Q4_InsertStudents {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/demo_db";
        String username = "root";
        String password = "root";

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
                System.out.println("Table 'Students' ready.\n");

                String[] insertStatements = {
                    "INSERT INTO Students (id, name, age, grade) VALUES (100, 'Aaditya', 20, 'A')",
                    "INSERT INTO Students (id, name, age, grade) VALUES (200, 'Sneha', 22, 'B')",
                    "INSERT INTO Students (id, name, age, grade) VALUES (300, 'Vikram', 21, 'A')",
                    "INSERT INTO Students (id, name, age, grade) VALUES (400, 'Neha', 23, 'C')",
                    "INSERT INTO Students (id, name, age, grade) VALUES (500, 'Arjun', 20, 'B')"
                };

                for (String sql : insertStatements) {
                    stmt.executeUpdate(sql);
                }
                System.out.println("Inserted 5 records into 'Students'.");

                ResultSet rs = stmt.executeQuery("SELECT * FROM Students");
                System.out.println("\n--- Students Table Records ---");
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id")
                            + " | Name: " + rs.getString("name")
                            + " | Age: " + rs.getInt("age")
                            + " | Grade: " + rs.getString("grade"));
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    }
}
