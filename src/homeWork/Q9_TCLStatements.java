package homeWork;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Q9_TCLStatements {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/employee_db";
        String username = "root";
        String password = "root";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(url, username, password);
                 Statement stmt = conn.createStatement()) {

                String createTable = "CREATE TABLE IF NOT EXISTS Employee ("
                        + "id INT PRIMARY KEY, "
                        + "name VARCHAR(100), "
                        + "salary DECIMAL(10,2))";
                stmt.executeUpdate(createTable);

                stmt.executeUpdate("DELETE FROM Employee");
                stmt.executeUpdate("INSERT INTO Employee VALUES (1, 'Rahul', 50000.00)");
                stmt.executeUpdate("INSERT INTO Employee VALUES (2, 'Priya', 60000.00)");
                stmt.executeUpdate("INSERT INTO Employee VALUES (3, 'Amit', 45000.00)");
                System.out.println("Initial Employee records:");
                printEmployees(stmt);

                System.out.println("\n========== TCL DEMONSTRATION ==========\n");

                System.out.println("1. Setting autoCommit to false...");
                conn.setAutoCommit(false);
                System.out.println("   autoCommit = " + conn.getAutoCommit());

                stmt.executeUpdate("INSERT INTO Employee VALUES (4, 'Neha', 55000.00)");
                System.out.println("   Inserted employee Neha (not yet committed).");

                System.out.println("\n2. Creating Savepoint SP1...");
                java.sql.Savepoint sp1 = conn.setSavepoint("SP1");
                System.out.println("   Savepoint SP1 created.");

                stmt.executeUpdate("INSERT INTO Employee VALUES (5, 'Vikram', 48000.00)");
                System.out.println("   Inserted employee Vikram.");

                System.out.println("\n3. Releasing Savepoint SP1...");
                conn.releaseSavepoint(sp1);
                System.out.println("   Savepoint SP1 released.");

                System.out.println("\n4. Committing the transaction...");
                conn.commit();
                System.out.println("   Transaction committed.");
                System.out.println("   Records after commit:");
                printEmployees(stmt);

                System.out.println("5. Rolling back a failed transaction...");
                conn.setAutoCommit(false);

                stmt.executeUpdate("INSERT INTO Employee VALUES (6, 'Suresh', 42000.00)");
                System.out.println("   Inserted employee Suresh.");

                java.sql.Savepoint sp2 = conn.setSavepoint("SP2");
                stmt.executeUpdate("INSERT INTO Employee VALUES (7, 'Kavita', 38000.00)");
                System.out.println("   Inserted employee Kavita.");

                System.out.println("   Rolling back to Savepoint SP2 (removes Kavita insert)...");
                conn.rollback(sp2);
                System.out.println("   Rolled back to SP2.");

                conn.commit();
                System.out.println("   Transaction committed.\n");
                System.out.println("   Records after rollback + commit:");
                printEmployees(stmt);

                conn.setAutoCommit(true);
                System.out.println("\n========== TCL DEMONSTRATION COMPLETE ==========");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    }

    private static void printEmployees(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM Employee");
        while (rs.next()) {
            System.out.println("  ID: " + rs.getInt("id")
                    + " | Name: " + rs.getString("name")
                    + " | Salary: " + rs.getDouble("salary"));
        }
    }
}
