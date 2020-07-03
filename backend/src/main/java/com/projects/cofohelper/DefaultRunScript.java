package com.projects.cofohelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.projects.cofohelper.dto.request.UserRegisterRequestDto;
import com.projects.cofohelper.service.user.UserService;

@Component
public class DefaultRunScript {

	@Autowired
	UserService userService;
	
	@EventListener(ContextRefreshedEvent.class)
	public void setDefaultUsers() {
		userService.register(new UserRegisterRequestDto("admin", "admin"));
		userService.register(new UserRegisterRequestDto("MochaLatte", "1"));
		userService.register(new UserRegisterRequestDto("bakpark", "1"));
		userService.register(new UserRegisterRequestDto("johny1", "1"));
		userService.register(new UserRegisterRequestDto("bonoky", "1"));
		userService.register(new UserRegisterRequestDto("sw1124.han", "1"));
		userService.register(new UserRegisterRequestDto("psykibaek", "1"));
		
		
		
	}
}
