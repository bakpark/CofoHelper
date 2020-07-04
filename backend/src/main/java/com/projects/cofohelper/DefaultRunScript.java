package com.projects.cofohelper;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.projects.cofohelper.domain.user.User;
import com.projects.cofohelper.domain.user.UserRepository;
import com.projects.cofohelper.dto.request.GroupRegisterRequestDto;
import com.projects.cofohelper.dto.request.InvitationAcceptDto;
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
	UserRepository userRepo;
	
	@Autowired
	GroupService groupService;
	
	@Autowired
	InvitationService invitationService;
	
	@Transactional
	@EventListener(ApplicationReadyEvent.class)
	public void setDefault() {
		setDefaultUsers();
		groupService.register(
				new GroupRegisterRequestDto("study"), "admin");
		inviteMembersToGroup(1L, "admin");
		acceptStudyGroupInvitation(acceptList());
		
		groupService.register(
				new GroupRegisterRequestDto("anonymous group1"), "anonymous");
		groupService.register(
				new GroupRegisterRequestDto("anonymous group2"), "anonymous");
		inviteMembersToGroup(2L, "anonymous");
		inviteMembersToGroup(3L, "anonymous");
		
	}
	
	
	// sub methods
	
	private void setDefaultUsers() {
		userService.register(new UserRegisterRequestDto("admin", "admin"));
		userService.register(new UserRegisterRequestDto("anonymous", "1"));
		userService.register(new UserRegisterRequestDto("MochaLatte", "1"));
		userService.register(new UserRegisterRequestDto("bakpark", "1"));
		userService.register(new UserRegisterRequestDto("johny1", "1"));
		userService.register(new UserRegisterRequestDto("bonoky", "1"));
		userService.register(new UserRegisterRequestDto("sw1124.han", "1"));
		userService.register(new UserRegisterRequestDto("psykibaek", "1"));
	}
	private void inviteMembersToGroup(Long groupId, String inviterHandle) {
		invitationService.register(new InvitationRegisterDto("MochaLatte", groupId), inviterHandle);
		invitationService.register(new InvitationRegisterDto("bakpark", groupId), inviterHandle);
		invitationService.register(new InvitationRegisterDto("johny1", groupId), inviterHandle);
		invitationService.register(new InvitationRegisterDto("bonoky", groupId), inviterHandle);
		invitationService.register(new InvitationRegisterDto("sw1124.han", groupId), inviterHandle);
		invitationService.register(new InvitationRegisterDto("psykibaek", groupId), inviterHandle);
	}
	private List<String> acceptList(){
		ArrayList<String> retList = new ArrayList<String>();
		retList.add("MochaLatte");
		retList.add("bakpark");
		retList.add("johny1");
		retList.add("bonoky");
		retList.add("sw1124.han");
		retList.add("psykibaek");
		return retList;
	}
	private void acceptStudyGroupInvitation(List<String> acceptList) {
		for(String handle : acceptList) {
			User user = userRepo.findByHandle(handle);
			invitationService.accept(new InvitationAcceptDto(user.getInvitations().get(0).getInvitationId()), handle);
		}
	}

}
