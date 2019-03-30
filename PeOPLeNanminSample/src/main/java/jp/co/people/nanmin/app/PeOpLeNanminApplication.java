package jp.co.people.nanmin.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@Configuration
@ImportResource("classpath:beans.xml")
public class PeOpLeNanminApplication {

	public static void main(String[] args) {
		SpringApplication.run(PeOpLeNanminApplication.class, args);
	}
}
