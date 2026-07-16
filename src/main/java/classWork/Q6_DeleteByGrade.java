package classWork;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Q6_DeleteByGrade {
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

                stmt.executeUpdate("DELETE FROM Students");
                stmt.executeUpdate("INSERT INTO Students (id, name, age, grade) VALUES (1, 'Amit', 20, 'A')");
                stmt.executeUpdate("INSERT INTO Students (id, name, age, grade) VALUES (2, 'Sneha', 22, 'B')");
                stmt.executeUpdate("INSERT INTO Students (id, name, age, grade) VALUES (3, 'Vikram', 21, 'C')");
                stmt.executeUpdate("INSERT INTO Students (id, name, age, grade) VALUES (4, 'Neha', 23, 'D')");
                stmt.executeUpdate("INSERT INTO Students (id, name, age, grade) VALUES (5, 'Arjun', 20, 'F')");
                System.out.println("Students before deletion:");
                printStudents(stmt);

                Scanner scanner = new Scanner(System.in);
                System.out.print("\nEnter grade threshold (delete grades below this, e.g. C): ");
                char threshold = scanner.nextLine().trim().toUpperCase().charAt(0);

                String deleteSQL = "DELETE FROM Students WHERE grade > ?";
                try (PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
                    pstmt.setString(1, String.valueOf(threshold));
                    int deleted = pstmt.executeUpdate();
                    System.out.println("\nDeleted " + deleted + " student(s) with grade below " + threshold + ".");
                }

                System.out.println("Students after deletion:");
                printStudents(stmt);
                scanner.close();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    }

    private static void printStudents(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM Students");
        System.out.println("  ---");
        while (rs.next()) {
            System.out.println("  ID: " + rs.getInt("id")
                    + " | Name: " + rs.getString("name")
                    + " | Age: " + rs.getInt("age")
                    + " | Grade: " + rs.getString("grade"));
        }
        System.out.println("  ---");
    }
}
