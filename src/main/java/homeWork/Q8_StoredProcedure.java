package homeWork;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Q8_StoredProcedure {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/company";
        String username = "root";
        String password = "root";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(url, username, password);
                 Statement stmt = conn.createStatement()) {

                String createTable = "CREATE TABLE IF NOT EXISTS employees ("
                        + "id INT PRIMARY KEY, "
                        + "name VARCHAR(100), "
                        + "department VARCHAR(100), "
                        + "salary DECIMAL(10,2))";
                stmt.executeUpdate(createTable);

                stmt.executeUpdate("DELETE FROM employees");
                stmt.executeUpdate("INSERT INTO employees VALUES (1, 'Alice', 'HR', 50000.00)");
                stmt.executeUpdate("INSERT INTO employees VALUES (2, 'Bob', 'HR', 45000.00)");
                stmt.executeUpdate("INSERT INTO employees VALUES (3, 'Charlie', 'IT', 60000.00)");
                stmt.executeUpdate("INSERT INTO employees VALUES (4, 'Diana', 'IT', 55000.00)");
                stmt.executeUpdate("INSERT INTO employees VALUES (5, 'Eve', 'Finance', 48000.00)");
                System.out.println("Sample employee data inserted.\n");

                String createProc = "DROP PROCEDURE IF EXISTS TotalSalary";
                stmt.executeUpdate(createProc);

                createProc = "CREATE PROCEDURE TotalSalary(IN dept VARCHAR(100), OUT total DECIMAL(10,2)) "
                        + "BEGIN "
                        + "  SELECT IFNULL(SUM(salary), 0) INTO total FROM employees WHERE department = dept; "
                        + "END";
                stmt.executeUpdate(createProc);
                System.out.println("Stored procedure 'TotalSalary' created.\n");

                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter department name: ");
                String department = scanner.nextLine();

                String callSQL = "{CALL TotalSalary(?, ?)}";
                try (CallableStatement cstmt = conn.prepareCall(callSQL)) {
                    cstmt.setString(1, department);
                    cstmt.registerOutParameter(2, java.sql.Types.DECIMAL);

                    cstmt.execute();

                    double totalSalary = cstmt.getDouble(2);
                    System.out.println("\nTotal salary for department '" + department + "': " + totalSalary);
                }

                scanner.close();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    }
}
