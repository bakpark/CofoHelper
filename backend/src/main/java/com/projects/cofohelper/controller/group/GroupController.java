package com.projects.cofohelper.controller.group;

import javax.servlet.http.HttpServletRequest;

import com.projects.cofohelper.exception.UnAuthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projects.cofohelper.common.Constants;
import com.projects.cofohelper.dto.request.GroupRegisterRequestDto;
import com.projects.cofohelper.dto.response.ResponseDataDto;
import com.projects.cofohelper.service.group.GroupService;
import com.projects.cofohelper.service.user.UserService;

@RestController
public class GroupController {

	@Autowired
	GroupService groupService;
	@Autowired
	UserService userService;

	@PostMapping(value = "/api/groups")
	ResponseEntity<ResponseDataDto> register(@RequestBody GroupRegisterRequestDto requestDto,
                                           HttpServletRequest request){
		String makerHandle = (String)request.getAttribute(Constants.USER_HANDLE);
		return ResponseEntity.ok()
				.body(new ResponseDataDto(HttpStatus.OK.value(), groupService.register(requestDto, makerHandle)));
	}

  @GetMapping(value = "/api/users/{handle}/groups")
  public ResponseEntity<ResponseDataDto> getGroups(@PathVariable String handle, HttpServletRequest request) {
    String loginHandle = (String) request.getAttribute(Constants.USER_HANDLE);
    if (handle.contentEquals(loginHandle))
      return ResponseEntity.ok()
        .body(new ResponseDataDto(HttpStatus.OK.value(), userService.getGroups(handle)));
    else
      throw new UnAuthorizedException("UnAuthorized for :" + handle);
  }

  @GetMapping(value = "/api/groups/{groupId}")
  public ResponseEntity<ResponseDataDto> getGroups(@PathVariable Long groupId) {
    return ResponseEntity.ok()
      .body(new ResponseDataDto(HttpStatus.OK.value(), groupService.getGroup(groupId)));

  }

	@GetMapping(value = "/api/group/{groupId}/invitations")
	ResponseEntity<ResponseDataDto> getInvitations(@PathVariable Long groupId, HttpServletRequest request){
		String requesterHandle = (String)request.getAttribute(Constants.USER_HANDLE);
		return ResponseEntity.ok()
				.body(new ResponseDataDto(HttpStatus.OK.value(), groupService.getInvitations(groupId, requesterHandle)));
	}
}
