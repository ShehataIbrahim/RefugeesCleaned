package jp.co.people.core.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@Configuration
@ImportResource("classpath:beans.xml")
public class PeoplecoreApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(PeoplecoreApplication.class, args);
	}
}
