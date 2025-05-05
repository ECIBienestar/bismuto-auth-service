package edu.eci.cvds.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Main application class for the authentication service.
 * <p>
 * This class serves as the entry point for the Spring Boot application.
 * It enables MongoDB repositories and runs the Spring Boot application.
 */
@SpringBootApplication
@EnableMongoRepositories(basePackages = "edu.eci.cvds.auth.repository")
public class AuthServiceApplication {

    /**
     * The main method that launches the Spring Boot application.
     * <p>
     * This method initializes the Spring Boot application context and starts the embedded web server.
     *
     * @param args command line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }
}
