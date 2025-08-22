package mk.ukim.finki.soa.masterthesis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MasterThesisApplication {

    public static void main(String[] args) {
        SpringApplication.run(MasterThesisApplication.class, args);
    }

}
