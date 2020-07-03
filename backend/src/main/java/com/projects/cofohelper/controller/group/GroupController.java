package com.projects.cofohelper.controller.group;

import com.projects.cofohelper.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.projects.cofohelper.dto.request.GroupRegisterRequestDto;
import com.projects.cofohelper.dto.response.GroupRegisterResponseDto;
import com.projects.cofohelper.service.group.GroupService;
import com.projects.cofohelper.service.user.UserService;

import javax.servlet.http.HttpServletRequest;

@RestController
public class GroupController {

	@Autowired
	GroupService groupService;
	@Autowired
	UserService userService;

	@PostMapping(value = "/groups")
	ResponseEntity<GroupRegisterResponseDto> register(@RequestBody GroupRegisterRequestDto requestDto, HttpServletRequest request){
	  String handle = (String)request.getAttribute(Constants.USER_HANDLE);
		return new ResponseEntity<GroupRegisterResponseDto>(groupService.register(requestDto, handle), HttpStatus.OK);
	}

}
