package ECSE428Project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class TriviaGameApplication {
    public static void main(String[] args) {
        SpringApplication.run(TriviaGameApplication.class, args);
    }
}
