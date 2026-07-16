package classWork;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Q5_MoneyTransfer {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/bank_db";
        String username = "root";
        String password = "aaditya@123";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(url, username, password);
                 Statement stmt = conn.createStatement()) {

                String createTable = "CREATE TABLE IF NOT EXISTS accounts ("
                        + "id INT PRIMARY KEY, "
                        + "name VARCHAR(100), "
                        + "balance DECIMAL(10,2))";
                stmt.executeUpdate(createTable);

                stmt.executeUpdate("DELETE FROM accounts");
                stmt.executeUpdate("INSERT INTO accounts (id, name, balance) VALUES (1, 'Ravi', 10000.00)");
                stmt.executeUpdate("INSERT INTO accounts (id, name, balance) VALUES (2, 'Suresh', 5000.00)");
                System.out.println("Initial balances:");
                printAccounts(stmt);

                conn.setAutoCommit(false);
                System.out.println("\n--- Starting Transaction ---");

                try {
                    stmt.executeUpdate("UPDATE accounts SET balance = balance - 3000 WHERE id = 1");
                    System.out.println("Debited 3000 from Ravi (Account 1).");

                    stmt.executeUpdate("UPDATE accounts SET balance = balance + 3000 WHERE id = 2");
                    System.out.println("Credited 3000 to Suresh (Account 2).");

                    conn.commit();
                    System.out.println("Transaction COMMITTED successfully.\n");
                } catch (SQLException e) {
                    conn.rollback();
                    System.out.println("Error occurred. Transaction ROLLED BACK: " + e.getMessage());
                }

                System.out.println("Final balances after transfer:");
                printAccounts(stmt);

                conn.setAutoCommit(true);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    }

    private static void printAccounts(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM accounts");
        while (rs.next()) {
            System.out.println("  Account " + rs.getInt("id")
                    + " (" + rs.getString("name") + "): Rs." + rs.getDouble("balance"));
        }
    }
}
