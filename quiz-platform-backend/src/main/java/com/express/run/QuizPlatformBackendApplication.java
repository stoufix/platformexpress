package com.express.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Represents the main of the application
 * 
 Maha.BSaid
 * @version 1.0
 */
@SpringBootApplication
@EntityScan("com.express.model")
@EnableJpaRepositories("com.express.repository")
@ComponentScan(basePackages = "com.express")
@EnableJpaAuditing
public class QuizPlatformBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizPlatformBackendApplication.class, args);

	}

}
