package day6.q54;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

@Component
public class DependencyCheck {

    private final DataSource dataSource;

    @Autowired
    public DependencyCheck(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
    public void checkConnection() throws Exception {
        System.out.println("=== Q54 - Maven Dependency Management ===");
        System.out.println("Successfully connected to database via Spring Boot auto-configured DataSource!");

        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData meta = conn.getMetaData();
            System.out.println("Database Product: " + meta.getDatabaseProductName());
            System.out.println("Database Version: " + meta.getDatabaseProductVersion());
            System.out.println("Driver: " + meta.getDriverName() + " " + meta.getDriverVersion());
            System.out.println("URL: " + meta.getURL());
        }
    }
}
