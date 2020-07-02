package com.projects.cofohelper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CofohelperApplication {

	public static void main(String[] args) {
		SpringApplication.run(CofohelperApplication.class, args);
	}

}
