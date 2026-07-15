package classWork;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Q2_EmpTable {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/demo_db";
        String username = "root";
        String password = "root";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(url, username, password);
                 Statement stmt = conn.createStatement()) {

                String createTable = "CREATE TABLE IF NOT EXISTS emp ("
                        + "id INT PRIMARY KEY, "
                        + "name VARCHAR(100), "
                        + "city VARCHAR(100))";
                stmt.executeUpdate(createTable);
                System.out.println("Table 'emp' created successfully.");

                String insert1 = "INSERT INTO emp (id, name, city) VALUES (1, 'Rahul', 'Delhi')";
                String insert2 = "INSERT INTO emp (id, name, city) VALUES (2, 'Priya', 'Mumbai')";
                stmt.executeUpdate(insert1);
                stmt.executeUpdate(insert2);
                System.out.println("Inserted 2 rows into 'emp'.");

                ResultSet rs = stmt.executeQuery("SELECT * FROM emp");
                System.out.println("\n--- emp Table Records ---");
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id")
                            + " | Name: " + rs.getString("name")
                            + " | City: " + rs.getString("city"));
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    }
}
