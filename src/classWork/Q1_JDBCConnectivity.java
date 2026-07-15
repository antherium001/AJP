package classWork;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Q1_JDBCConnectivity {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/demo_db";
        String username = "root";
        String password = "root";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("JDBC Driver loaded successfully!");

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

                String insert1 = "INSERT INTO employees (id, name, age, salary) VALUES (1, 'Alice', 30, 50000.00)";
                String insert2 = "INSERT INTO employees (id, name, age, salary) VALUES (2, 'Bob', 28, 45000.00)";
                stmt.executeUpdate(insert1);
                stmt.executeUpdate(insert2);
                System.out.println("Inserted 2 records into 'employees'.");

                String selectQuery = "SELECT * FROM employees";
                ResultSet rs = stmt.executeQuery(selectQuery);
                System.out.println("\n--- Employee Records ---");
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id")
                            + " | Name: " + rs.getString("name")
                            + " | Age: " + rs.getInt("age")
                            + " | Salary: " + rs.getDouble("salary"));
                }

                String updateQuery = "UPDATE employees SET salary = 52000.00 WHERE id = 1";
                int rowsUpdated = stmt.executeUpdate(updateQuery);
                System.out.println("\nRows updated: " + rowsUpdated);

                String deleteQuery = "DELETE FROM employees WHERE id = 2";
                int rowsDeleted = stmt.executeUpdate(deleteQuery);
                System.out.println("Rows deleted: " + rowsDeleted);

                System.out.println("\nAll JDBC operations completed successfully!");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    }
}
