package com.projects.cofohelper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.projects.cofohelper.domain.user.User;
import com.projects.cofohelper.domain.user.UserRepository;
import com.projects.cofohelper.dto.request.ContestProblemRegisterDto;
import com.projects.cofohelper.dto.request.ContestRegisterDto;
import com.projects.cofohelper.dto.request.GroupRegisterRequestDto;
import com.projects.cofohelper.dto.request.InvitationAcceptDto;
import com.projects.cofohelper.dto.request.InvitationRegisterDto;
import com.projects.cofohelper.dto.request.ProblemRegisterDto;
import com.projects.cofohelper.dto.request.UserRegisterRequestDto;
import com.projects.cofohelper.service.contest.ContestService;
import com.projects.cofohelper.service.group.GroupService;
import com.projects.cofohelper.service.invitation.InvitationService;
import com.projects.cofohelper.service.problem.ProblemService;
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

	@Autowired
	ContestService contestService;

	@Autowired
	ProblemService problemService;

	@Transactional
	@EventListener(ApplicationReadyEvent.class)
	public void setDefault() throws Exception{
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
		setDefaultContestAndProblem();
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
			invitationService.accept(user.getInvitations().get(0).getInvitationId(), handle);
		}
	}

	private void setDefaultContestAndProblem() throws Exception{
		contestService.register(1L, new ContestRegisterDto("1353 TEST", LocalDateTime.now().plusDays(3)), "admin");
		problemService.register(new ProblemRegisterDto("1353-A"));
		problemService.register(new ProblemRegisterDto("1353-B"));
		problemService.register(new ProblemRegisterDto("1353-C"));
		problemService.register(new ProblemRegisterDto("1353-D"));
		problemService.register(new ProblemRegisterDto("1353-E"));
		problemService.register(new ProblemRegisterDto("1353-F"));

		contestService.register(1L, new ContestRegisterDto("1360 TEST", LocalDateTime.now().plusDays(3)), "admin");
		problemService.register(new ProblemRegisterDto("1360-A"));
		problemService.register(new ProblemRegisterDto("1360-B"));
		problemService.register(new ProblemRegisterDto("1360-C"));
		problemService.register(new ProblemRegisterDto("1360-D"));

		contestService.addProblem(1L, new ContestProblemRegisterDto("1353-A"), "admin");
		contestService.addProblem(1L, new ContestProblemRegisterDto("1353-B"), "admin");
		contestService.addProblem(1L, new ContestProblemRegisterDto("1353-C"), "admin");
		contestService.addProblem(1L, new ContestProblemRegisterDto("1353-D"), "admin");
		contestService.addProblem(1L, new ContestProblemRegisterDto("1353-E"), "admin");
		contestService.addProblem(1L, new ContestProblemRegisterDto("1353-F"), "admin");
		contestService.addProblem(2L, new ContestProblemRegisterDto("1360-A"), "admin");
		contestService.addProblem(2L, new ContestProblemRegisterDto("1360-B"), "admin");
		contestService.addProblem(2L, new ContestProblemRegisterDto("1360-C"), "admin");
		contestService.addProblem(2L, new ContestProblemRegisterDto("1360-D"), "admin");
	}

}
