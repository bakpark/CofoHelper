package com.projects.cofohelper.controller.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projects.cofohelper.domain.partyinfo.PartyInfoRepository;
import com.projects.cofohelper.dto.response.ResponseDataDto;
import com.projects.cofohelper.service.group.GroupService;
import com.projects.cofohelper.service.invitation.InvitationService;
import com.projects.cofohelper.service.user.UserService;

@RestController
public class TestController {
	
	
	@Autowired
	GroupService groupService;
	@Autowired
	InvitationService invitationService;
	@Autowired
	UserService userService;
	
	@Autowired
	PartyInfoRepository partyInfoRepo;
	
//	@Transactional
//	@GetMapping(value = "/test")
//	public ResponseEntity<ResponseDataDto> runScript1(){
//		groupService.register(new GroupRegisterRequestDto("group1"), "admin");
//		invitationService.register(new InvitationRegisterDto("bakpark", 1L), "admin");
//		invitationService.register(new InvitationRegisterDto("bonoky", 1L), "admin");
//		invitationService.register(new InvitationRegisterDto("johny1", 1L), "admin");
//
//		groupService.register(new GroupRegisterRequestDto("group2"), "psykibaek");
//		invitationService.register(new InvitationRegisterDto("bakpark", 2L), "psykibaek");
//		invitationService.register(new InvitationRegisterDto("bonoky", 2L), "psykibaek");
//		invitationService.register(new InvitationRegisterDto("johny1", 2L), "psykibaek");
//		
//		return ResponseEntity.ok().body(new ResponseDataDto(HttpStatus.OK.value(), Boolean.TRUE));
//	}
	
	@GetMapping(value = "/test/users")
	public ResponseEntity<ResponseDataDto> getAllUsers(){
		return ResponseEntity.ok()
				.body(new ResponseDataDto(HttpStatus.OK.value(), userService.getAll()));
	}

	@GetMapping(value = "/test/groups")
	public ResponseEntity<ResponseDataDto> getAllGroups(){
		return ResponseEntity.ok()
				.body(new ResponseDataDto(HttpStatus.OK.value(), groupService.getAll()));
	}

	@GetMapping(value = "/test/invitations")
	public ResponseEntity<ResponseDataDto> getAllInvitations(){
		return ResponseEntity.ok()
				.body(new ResponseDataDto(HttpStatus.OK.value(), invitationService.getAll()));
	}
	
	@GetMapping(value = "/test/partyinfos")
	public ResponseEntity<ResponseDataDto> getAllPartyInfos(){
		return ResponseEntity.ok()
				.body(new ResponseDataDto(HttpStatus.OK.value(), partyInfoRepo.findAll()));
	}
}
