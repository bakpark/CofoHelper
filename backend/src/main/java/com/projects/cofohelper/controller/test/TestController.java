package com.projects.cofohelper.controller.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projects.cofohelper.domain.contest.ContestRepository;
import com.projects.cofohelper.domain.partyinfo.PartyInfoRepository;
import com.projects.cofohelper.domain.problem.ProblemRepository;
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
	ProblemRepository problemRepo;
	@Autowired
	ContestRepository contestRepo;
	
	@Autowired
	PartyInfoRepository partyInfoRepo;
	
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
	
	@GetMapping(value = "/test/problems")
	public ResponseEntity<ResponseDataDto> getAllProblems(){
		return ResponseEntity.ok()
				.body(new ResponseDataDto(HttpStatus.OK.value(), problemRepo.findAll()));
	}
	
	@GetMapping(value = "/test/contests")
	public ResponseEntity<ResponseDataDto> getAllContests(){
		return ResponseEntity.ok()
				.body(new ResponseDataDto(HttpStatus.OK.value(), contestRepo.findAll()));
	}
}
