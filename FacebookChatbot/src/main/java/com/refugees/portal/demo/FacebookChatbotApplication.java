package com.refugees.portal.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.github.messenger4j.MessengerPlatform;
import com.github.messenger4j.send.MessengerSendClient;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.refugees.portal.**"})
public class FacebookChatbotApplication {
	
	@Bean
	public MessengerSendClient messengerSendClient(@Value("${fb.pageAccessToken}") String pageAccessToken) {
		
		return MessengerPlatform.newSendClientBuilder(pageAccessToken).build();
	}
	public static void main(String[] args) {
		SpringApplication.run(FacebookChatbotApplication.class, args);
	}

}

