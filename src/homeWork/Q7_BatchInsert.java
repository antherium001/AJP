package homeWork;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Q7_BatchInsert {
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
                stmt.executeUpdate("DELETE FROM Students");

                String insertSQL = "INSERT INTO Students (id, name, age, grade) VALUES (?, ?, ?, ?)";

                try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                    conn.setAutoCommit(false);

                    Object[][] records = {
                        {1, "Amit", 20, "A"},
                        {2, "Sneha", 22, "B"},
                        {3, "Vikram", 21, "A"},
                        {4, "Neha", 23, "C"},
                        {5, "Arjun", 20, "B"},
                        {6, "Priya", 24, "A"},
                        {7, "Rohan", 22, "D"},
                        {8, "Kavita", 21, "B"}
                    };

                    for (Object[] row : records) {
                        pstmt.setInt(1, (int) row[0]);
                        pstmt.setString(2, (String) row[1]);
                        pstmt.setInt(3, (int) row[2]);
                        pstmt.setString(4, (String) row[3]);
                        pstmt.addBatch();
                    }

                    int[] results = pstmt.executeBatch();
                    conn.commit();
                    conn.setAutoCommit(true);

                    System.out.println("Batch execution completed.");
                    System.out.println("Records inserted: " + results.length);
                }

                System.out.println("\n--- Students Table (after batch insert) ---");
                ResultSet rs = stmt.executeQuery("SELECT * FROM Students");
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
