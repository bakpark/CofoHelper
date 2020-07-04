package com.projects.cofohelper.controller.Invitation;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.projects.cofohelper.common.Constants;
import com.projects.cofohelper.dto.request.InvitationAcceptDto;
import com.projects.cofohelper.dto.request.InvitationRegisterDto;
import com.projects.cofohelper.dto.response.ResponseDataDto;
import com.projects.cofohelper.service.invitation.InvitationService;

@RestController
public class InvitationController {

	@Autowired
	InvitationService invitationService;
	
	
	@PostMapping("/invitations")
	public ResponseEntity<ResponseDataDto> register(@RequestBody InvitationRegisterDto requestDto, HttpServletRequest request){
		String inviter = (String)request.getAttribute(Constants.USER_HANDLE);
		return ResponseEntity.ok()
				.body(new ResponseDataDto(HttpStatus.OK.value(), invitationService.register(requestDto, inviter)));
	}
	
	@DeleteMapping("/invitations")
	public ResponseEntity<ResponseDataDto> delete(Long invitationId, HttpServletRequest request){
		String requester = (String)request.getAttribute(Constants.USER_HANDLE);
		invitationService.delete(invitationId, requester);
		return ResponseEntity.ok()
				.body(new ResponseDataDto(HttpStatus.OK.value()));
	}
	
	@PostMapping("/invitation/accept")
	public ResponseEntity<ResponseDataDto> accept(@RequestBody InvitationAcceptDto requestDto, HttpServletRequest request){
		String requester = (String)request.getAttribute(Constants.USER_HANDLE);
		invitationService.accept(requestDto, requester);
		return ResponseEntity.ok()
				.body(new ResponseDataDto(HttpStatus.OK.value()));
	}
}
