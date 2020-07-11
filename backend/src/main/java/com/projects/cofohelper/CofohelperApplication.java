package com.projects.cofohelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.projects.cofohelper.dto.request.UserRegisterRequestDto;
import com.projects.cofohelper.service.user.UserService;

@SpringBootApplication
@EnableJpaAuditing
public class CofohelperApplication {

	public static void main(String[] args) {
		SpringApplication.run(CofohelperApplication.class, args);
	}

}
