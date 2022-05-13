package africa.semicolon.phoneBookApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan("africa.semicolon.phoneBookApp")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
