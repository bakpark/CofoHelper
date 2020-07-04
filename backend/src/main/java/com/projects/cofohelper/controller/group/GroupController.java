package com.projects.cofohelper.controller.group;

import javax.servlet.http.HttpServletRequest;
import com.projects.cofohelper.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.projects.cofohelper.common.Constants;
import com.projects.cofohelper.domain.group.Group;
import com.projects.cofohelper.dto.request.GroupRegisterRequestDto;
import com.projects.cofohelper.dto.response.ResponseDataDto;
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
	ResponseEntity<ResponseDataDto> register(@RequestBody GroupRegisterRequestDto requestDto, HttpServletRequest request){
		String makerHandle = (String)request.getAttribute(Constants.USER_HANDLE);
		return ResponseEntity.ok()
				.body(new ResponseDataDto(HttpStatus.OK.value(), groupService.register(requestDto, makerHandle)));
	}

}
