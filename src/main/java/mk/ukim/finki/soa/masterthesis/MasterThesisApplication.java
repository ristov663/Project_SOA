package mk.ukim.finki.soa.masterthesis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration.class})
@SpringBootApplication
public class MasterThesisApplication {

	public static void main(String[] args) {
		SpringApplication.run(MasterThesisApplication.class, args);
	}

}
