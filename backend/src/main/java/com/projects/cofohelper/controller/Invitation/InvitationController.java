package com.projects.cofohelper.controller.Invitation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.projects.cofohelper.domain.invitation.Invitation;
import com.projects.cofohelper.dto.InvitationRegisterDto;
import com.projects.cofohelper.service.invitation.InvitationService;

@RestController
public class InvitationController {

	@Autowired
	InvitationService invitationService;
	
	@PostMapping("/invitations")
	public ResponseEntity<Invitation> register(@RequestBody InvitationRegisterDto request){
		return new ResponseEntity<Invitation>(invitationService.register(request),HttpStatus.OK); 
	}
}
