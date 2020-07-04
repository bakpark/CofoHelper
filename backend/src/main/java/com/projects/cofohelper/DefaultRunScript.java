package com.projects.cofohelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.projects.cofohelper.dto.request.GroupRegisterRequestDto;
import com.projects.cofohelper.dto.request.InvitationRegisterDto;
import com.projects.cofohelper.dto.request.UserRegisterRequestDto;
import com.projects.cofohelper.service.group.GroupService;
import com.projects.cofohelper.service.invitation.InvitationService;
import com.projects.cofohelper.service.user.UserService;

@Component
public class DefaultRunScript {

	@Autowired
	UserService userService;
	
	@Autowired
	GroupService groupService;
	
	@Autowired
	InvitationService invitationService;
	
	@EventListener(ApplicationReadyEvent.class)
	public void setDefaultUsers() {
		userService.register(new UserRegisterRequestDto("admin", "admin"));
		userService.register(new UserRegisterRequestDto("MochaLatte", "1"));
		userService.register(new UserRegisterRequestDto("bakpark", "1"));
		userService.register(new UserRegisterRequestDto("johny1", "1"));
		userService.register(new UserRegisterRequestDto("bonoky", "1"));
		userService.register(new UserRegisterRequestDto("sw1124.han", "1"));
		userService.register(new UserRegisterRequestDto("psykibaek", "1"));
		
		
	}

	@EventListener(ApplicationReadyEvent.class)
	public void makeTwoGroupsAndInvitesUsers() {
//		groupService.register(
//				new GroupRegisterRequestDto("group1"), "admin");
//		invitationService.register(new InvitationRegisterDto("bakpark", 1L), "admin");
//		invitationService.register(new InvitationRegisterDto("bonoky", 1L), "admin");
//		invitationService.register(new InvitationRegisterDto("johny1", 1L), "admin");
//		
//
//		groupService.register(
//				new GroupRegisterRequestDto("group2"), "psykibaek");
//		invitationService.register(new InvitationRegisterDto("bakpark", 2L), "psykibaek");
//		invitationService.register(new InvitationRegisterDto("bonoky", 2L), "psykibaek");
//		invitationService.register(new InvitationRegisterDto("johny1", 2L), "psykibaek");
	}
}
