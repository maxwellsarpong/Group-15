package com.mallon.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.web.client.RestTemplate;
//import org.springframework.web.reactive.function.client.WebClient;

//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;

@SpringBootApplication
public class DemoApplication {

	//required bean for the rest template
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}







	public static void main(String[] args) {
		
		// Start the RESTFul services
		SpringApplication.run(DemoApplication.class, args);


		
	}


}
