package com.altran.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.altran.model")
@EnableJpaRepositories("com.altran.repository")
@ComponentScan(basePackages = "com.altran")
@EnableJpaAuditing
public class CandidatePlatformBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CandidatePlatformBackendApplication.class, args);
	}

}
