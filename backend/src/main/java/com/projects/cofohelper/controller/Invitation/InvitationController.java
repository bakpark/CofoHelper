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
	public ResponseEntity<ResponseDataDto> register(@RequestBody InvitationRegisterDto requestDto, HttpServletRequest request){
		String inviter = (String)request.getAttribute(Constants.USER_HANDLE);
		return ResponseEntity.ok()
				.body(new ResponseDataDto(HttpStatus.OK.value(), invitationService.register(requestDto, inviter)));
	}

	@DeleteMapping("/api/invitations")
	public ResponseEntity<ResponseDataDto> delete(Long invitationId, HttpServletRequest request){
		String requester = (String)request.getAttribute(Constants.USER_HANDLE);
		invitationService.delete(invitationId, requester);
		return ResponseEntity.ok()
				.body(new ResponseDataDto(HttpStatus.OK.value()));
	}

	@PostMapping("/api/invitation/accept")
	public ResponseEntity<ResponseDataDto> accept(@RequestBody InvitationAcceptDto requestDto, HttpServletRequest request){
		String requester = (String)request.getAttribute(Constants.USER_HANDLE);
		invitationService.accept(requestDto, requester);
		return ResponseEntity.ok()
				.body(new ResponseDataDto(HttpStatus.OK.value()));
	}

  @GetMapping(value = "/api/user/invitations")
  public ResponseEntity<ResponseDataDto> getInvitations(String handle, HttpServletRequest request) {
    String loginHandle = (String) request.getAttribute(Constants.USER_HANDLE);
    if (handle.equals(loginHandle))
      return ResponseEntity.ok()
        .body(new ResponseDataDto(HttpStatus.OK.value(), userService.getInvitations(handle)));
    else
      throw new UnAuthorizedException("UnAuthorized for :" + handle);

  }
}
