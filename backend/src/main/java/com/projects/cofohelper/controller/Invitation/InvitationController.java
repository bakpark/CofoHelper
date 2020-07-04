package com.projects.cofohelper.controller.Invitation;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.projects.cofohelper.common.Constants;
import com.projects.cofohelper.domain.invitation.Invitation;
import com.projects.cofohelper.dto.request.InvitationRegisterDto;
import com.projects.cofohelper.service.invitation.InvitationService;

@RestController
public class InvitationController {

	@Autowired
	InvitationService invitationService;
	
	
	@PostMapping("/invitations")
	public ResponseEntity<Invitation> register(@RequestBody InvitationRegisterDto requestDto, HttpServletRequest request){
		String inviter = (String)request.getAttribute(Constants.USER_HANDLE);
		return new ResponseEntity<Invitation>(invitationService.register(requestDto, inviter),HttpStatus.OK); 
	}
	
	// login user가 받은 모든 invitation을 줌
	@GetMapping("/invitations")
	public ResponseEntity<List<Invitation>> getAll(HttpServletRequest request){
		String handle = (String)request.getAttribute(Constants.USER_HANDLE);
		return new ResponseEntity<List<Invitation>>(invitationService.getAll(handle), HttpStatus.OK);
	}
}
