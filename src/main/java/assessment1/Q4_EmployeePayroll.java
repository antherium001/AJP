package assessment1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Q4_EmployeePayroll {
    static final String DB_URL = "jdbc:mysql://localhost:3306/demo_db";
    static final String DB_USER = "root";
    static final String DB_PASS = "aaditya@123";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL Driver loaded successfully!");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver not found: " + e.getMessage());
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             Statement stmt = conn.createStatement()) {

            System.out.println("Connected to database: demo_db\n");

            String createTable = "CREATE TABLE IF NOT EXISTS employee_payroll ("
                    + "emp_id INT PRIMARY KEY, "
                    + "emp_name VARCHAR(100), "
                    + "department VARCHAR(100), "
                    + "salary DECIMAL(10,2), "
                    + "city VARCHAR(100))";
            stmt.executeUpdate(createTable);
            System.out.println("Table 'employee_payroll' created/verified.\n");

            Scanner sc = new Scanner(System.in);
            boolean running = true;

            while (running) {
                System.out.println("========================================");
                System.out.println("   Employee Payroll Management System");
                System.out.println("========================================");
                System.out.println("1. Insert Employee Details");
                System.out.println("2. Display Employees with Salary > 50,000");
                System.out.println("3. Update Employee Salary");
                System.out.println("4. Delete Employee Record");
                System.out.println("5. Display Total Number of Employees");
                System.out.println("6. Display All Employees");
                System.out.println("7. Exit");
                System.out.print("Enter your choice: ");

                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        insertEmployee(conn, sc);
                        break;
                    case 2:
                        displayHighSalary(conn);
                        break;
                    case 3:
                        updateSalary(conn, sc);
                        break;
                    case 4:
                        deleteEmployee(conn, sc);
                        break;
                    case 5:
                        displayTotalCount(conn);
                        break;
                    case 6:
                        displayAllEmployees(conn);
                        break;
                    case 7:
                        running = false;
                        System.out.println("\nExiting... Thank you!");
                        break;
                    default:
                        System.out.println("\nInvalid choice. Please try again.");
                }
                System.out.println();
            }

        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    }

    static void insertEmployee(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter Employee ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Department: ");
        String dept = sc.nextLine();
        System.out.print("Enter Salary: ");
        double salary = sc.nextDouble();
        sc.nextLine();
        System.out.print("Enter City: ");
        String city = sc.nextLine();

        String sql = "INSERT INTO employee_payroll (emp_id, emp_name, department, salary, city) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, dept);
            pstmt.setDouble(4, salary);
            pstmt.setString(5, city);
            int rows = pstmt.executeUpdate();
            System.out.println("Employee inserted successfully. Rows affected: " + rows);
        }
    }

    static void displayHighSalary(Connection conn) throws SQLException {
        String sql = "SELECT * FROM employee_payroll WHERE salary > 50000";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("\n--- Employees with Salary > 50,000 ---");
            System.out.printf("%-8s %-25s %-15s %-12s %-15s%n", "ID", "Name", "Department", "Salary", "City");
            System.out.println("-----------------------------------------------------------------------");

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.printf("%-8d %-25s %-15s %-12.2f %-15s%n",
                        rs.getInt("emp_id"),
                        rs.getString("emp_name"),
                        rs.getString("department"),
                        rs.getDouble("salary"),
                        rs.getString("city"));
            }

            if (!found) {
                System.out.println("No employees found with salary greater than 50,000.");
            }
        }
    }

    static void updateSalary(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter Employee ID to update salary: ");
        int id = sc.nextInt();
        System.out.print("Enter new Salary: ");
        double newSalary = sc.nextDouble();
        sc.nextLine();

        String sql = "UPDATE employee_payroll SET salary = ? WHERE emp_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, newSalary);
            pstmt.setInt(2, id);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Salary updated successfully for Employee ID " + id + ".");
            } else {
                System.out.println("Employee with ID " + id + " not found.");
            }
        }
    }

    static void deleteEmployee(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter Employee ID to delete: ");
        int id = sc.nextInt();
        sc.nextLine();

        String sql = "DELETE FROM employee_payroll WHERE emp_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Employee with ID " + id + " deleted successfully.");
            } else {
                System.out.println("Employee with ID " + id + " not found.");
            }
        }
    }

    static void displayTotalCount(Connection conn) throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM employee_payroll";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                System.out.println("Total number of employees: " + rs.getInt("total"));
            }
        }
    }

    static void displayAllEmployees(Connection conn) throws SQLException {
        String sql = "SELECT * FROM employee_payroll";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n--- All Employees ---");
            System.out.printf("%-8s %-25s %-15s %-12s %-15s%n", "ID", "Name", "Department", "Salary", "City");
            System.out.println("-----------------------------------------------------------------------");

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.printf("%-8d %-25s %-15s %-12.2f %-15s%n",
                        rs.getInt("emp_id"),
                        rs.getString("emp_name"),
                        rs.getString("department"),
                        rs.getDouble("salary"),
                        rs.getString("city"));
            }

            if (!found) {
                System.out.println("No employees in the database.");
            }
        }
    }
}
