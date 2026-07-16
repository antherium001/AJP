package assessment1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Q1_BatchInsert {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/demo_db";
        String username = "root";
        String password = "aaditya@123";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL Driver loaded successfully!\n");

            try (Connection conn = DriverManager.getConnection(url, username, password);
                 Statement stmt = conn.createStatement()) {

                System.out.println("Connected to database: demo_db\n");

                String createTable = "CREATE TABLE IF NOT EXISTS employees ("
                        + "id INT PRIMARY KEY, "
                        + "name VARCHAR(100), "
                        + "age INT, "
                        + "salary DECIMAL(10,2))";
                stmt.executeUpdate(createTable);
                System.out.println("Table 'employees' created/verified.");

                stmt.executeUpdate("DELETE FROM employees");
                System.out.println("Cleared existing records.\n");

                String insertSQL = "INSERT INTO employees (id, name, age, salary) VALUES (?, ?, ?, ?)";

                try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                    conn.setAutoCommit(false);

                    Object[][] records = {
                        {1, "Amit Sharma", 28, 55000.00},
                        {2, "Sneha Patel", 24, 48000.00},
                        {3, "Vikram Singh", 30, 62000.00},
                        {4, "Neha Gupta", 26, 45000.00},
                        {5, "Arjun Mehta", 29, 72000.00},
                        {6, "Priya Verma", 27, 51000.00},
                        {7, "Rohan Das", 32, 58000.00},
                        {8, "Kavita Joshi", 25, 47000.00}
                    };

                    System.out.println("Adding " + records.length + " records to batch...");
                    for (Object[] row : records) {
                        pstmt.setInt(1, (int) row[0]);
                        pstmt.setString(2, (String) row[1]);
                        pstmt.setInt(3, (int) row[2]);
                        pstmt.setDouble(4, (double) row[3]);
                        pstmt.addBatch();
                        System.out.println("  Added: " + row[1]);
                    }

                    System.out.println("\nExecuting batch insert...");
                    int[] results = pstmt.executeBatch();
                    conn.commit();
                    conn.setAutoCommit(true);

                    System.out.println("Batch execution completed successfully!");
                    System.out.println("Records inserted: " + results.length);
                }

                System.out.println("\n--- Employees Table (after batch insert) ---");
                System.out.printf("%-5s %-20s %-6s %-12s%n", "ID", "Name", "Age", "Salary");
                System.out.println("--------------------------------------------");

                ResultSet rs = stmt.executeQuery("SELECT * FROM employees");
                while (rs.next()) {
                    System.out.printf("%-5d %-20s %-6d %-12.2f%n",
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("age"),
                            rs.getDouble("salary"));
                }

                System.out.println("\nAll batch operations completed successfully!");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    }
}
