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
	public void setAdminUser() {
		userService.register(new UserRegisterRequestDto("admin", "admin"));
	}
}
