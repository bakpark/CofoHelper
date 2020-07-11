package com.projects.cofohelper.controller.Invitation;

import javax.servlet.http.HttpServletRequest;

import com.projects.cofohelper.exception.UnAuthorizedException;
import com.projects.cofohelper.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projects.cofohelper.common.Constants;
import com.projects.cofohelper.dto.request.InvitationAcceptDto;
import com.projects.cofohelper.dto.request.InvitationRegisterDto;
import com.projects.cofohelper.dto.response.ResponseDataDto;
import com.projects.cofohelper.service.invitation.InvitationService;

@RestController
public class InvitationController {

	@Autowired
	InvitationService invitationService;

	@Autowired
	UserService userService;

	@PostMapping("/api/invitations")
	public ResponseEntity<ResponseDataDto> register(@RequestBody InvitationRegisterDto requestDto,
			HttpServletRequest request) {
		String inviterHandle = (String) request.getAttribute(Constants.USER_HANDLE);
		return ResponseEntity.ok().body(
				new ResponseDataDto(HttpStatus.OK.value(), invitationService.register(requestDto, inviterHandle)));
	}

	@DeleteMapping("/api/invitations/{invitationId}")
	public ResponseEntity<ResponseDataDto> delete(@PathVariable Long invitationId, HttpServletRequest request) {
		String requesterHandle = (String) request.getAttribute(Constants.USER_HANDLE);
		invitationService.delete(invitationId, requesterHandle);
		return ResponseEntity.ok().body(new ResponseDataDto(HttpStatus.OK.value()));
	}

	@GetMapping(value = "/api/users/{handle}/invitations")
	public ResponseEntity<ResponseDataDto> getInvitations(@PathVariable String handle, HttpServletRequest request) {
		String requesterHandle = (String) request.getAttribute(Constants.USER_HANDLE);
		return ResponseEntity.ok()
				.body(new ResponseDataDto(HttpStatus.OK.value(), userService.getInvitations(handle, requesterHandle)));
	}

	@PostMapping("/api/invitations/{invitationId}/accept")
	public ResponseEntity<ResponseDataDto> accept(@PathVariable Long invitationId,
			HttpServletRequest request) {
		String requesterHandle = (String) request.getAttribute(Constants.USER_HANDLE);
		invitationService.accept(invitationId, requesterHandle);
		return ResponseEntity.ok().body(new ResponseDataDto(HttpStatus.OK.value()));
	}

}
