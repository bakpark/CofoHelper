package com.projects.cofohelper.controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.projects.cofohelper.common.Constants;
import com.projects.cofohelper.domain.group.Group;
import com.projects.cofohelper.domain.invitation.Invitation;
import com.projects.cofohelper.domain.user.User;
import com.projects.cofohelper.dto.request.UserLoginRequestDto;
import com.projects.cofohelper.dto.request.UserRegisterRequestDto;
import com.projects.cofohelper.dto.response.ResponseDataDto;
import com.projects.cofohelper.exception.UnAuthorizedException;
import com.projects.cofohelper.service.JwtService;
import com.projects.cofohelper.service.user.UserService;

@RestController
public class UserController {
	@Autowired
	UserService userService;
	@Autowired
	JwtService jwtService;

	@PostMapping(value = "/users")
	public ResponseEntity<ResponseDataDto> register(@RequestBody UserRegisterRequestDto request) {
		return ResponseEntity.ok()
				.body(new ResponseDataDto(HttpStatus.OK.value(), userService.register(request)));
	}

	@PostMapping(value = "/users/login")
	public ResponseEntity<ResponseDataDto> login(@RequestBody UserLoginRequestDto request, HttpServletResponse response) {
		User user = userService.login(request);
		response.setHeader(Constants.AUTHORIZATION, jwtService.create(user.getHandle()));
		return ResponseEntity.ok()
				.body(new ResponseDataDto(HttpStatus.OK.value(), user));
	}

	@GetMapping(value = "/user/invitations")
	public ResponseEntity<ResponseDataDto> getInvitations(String handle, HttpServletRequest request) {
		String loginHandle = (String) request.getAttribute(Constants.USER_HANDLE);
		if (handle.equals(loginHandle))
			return ResponseEntity.ok()
					.body(new ResponseDataDto(HttpStatus.OK.value(), userService.getInvitations(handle)));
		else
			throw new UnAuthorizedException("UnAuthorized for :" + handle);

	}

	@GetMapping(value = "/user/groups")
	public ResponseEntity<ResponseDataDto> getGroups(String handle, HttpServletRequest request) {
		String loginHandle = (String) request.getAttribute(Constants.USER_HANDLE);
		if (handle.contentEquals(loginHandle))
			return ResponseEntity.ok()
					.body(new ResponseDataDto(HttpStatus.OK.value(), userService.getGroups(handle)));
		else
			throw new UnAuthorizedException("UnAuthorized for :" + handle);

	}

	@PostMapping(value = "/users/user_info")
	public ResponseEntity<ResponseDataDto> login(HttpServletRequest request, HttpServletResponse response) {
		String handle = (String) request.getAttribute(Constants.USER_HANDLE);
		return ResponseEntity.ok(new ResponseDataDto(HttpStatus.OK.value(), "", handle));
	}

}
