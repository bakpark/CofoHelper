package com.projects.cofohelper.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.projects.cofohelper.dto.response.ResponseDataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projects.cofohelper.common.Constants;
import com.projects.cofohelper.domain.user.User;
import com.projects.cofohelper.dto.request.UserLoginRequestDto;
import com.projects.cofohelper.dto.request.UserRegisterRequestDto;
import com.projects.cofohelper.dto.response.UserLoginResponseDto;
import com.projects.cofohelper.dto.response.UserRegisterResponseDto;
import com.projects.cofohelper.service.JwtService;
import com.projects.cofohelper.service.user.UserService;

@RestController
public class UserController {
  @Autowired
  UserService userService;
  @Autowired
  JwtService jwtService;
  @PostMapping(value = "/users")
  public ResponseEntity<User> register(@RequestBody UserRegisterRequestDto request){
//	  User user = userService.register(request);
//	  UserRegisterResponseDto responseDto =
//			  new UserRegisterResponseDto(user.getUserId(),user.getHandle());
//	  return new ResponseEntity<UserRegisterResponseDto>(responseDto, HttpStatus.OK);
	  return new ResponseEntity<User>(userService.register(request), HttpStatus.OK);
  }

  @PostMapping(value = "/users/login")
  public ResponseEntity<User>login(
		  @RequestBody UserLoginRequestDto request, HttpServletResponse response){
//	  User user = userService.login(request);
//	  UserLoginResponseDto responseDto =
//			  new UserLoginResponseDto(user.getUserId(), user.getHandle());
	  User user = userService.login(request);
	  response.setHeader(Constants.AUTHORIZATION, jwtService.create(user.getHandle()));
	  return new ResponseEntity<User>(user, HttpStatus.OK);
  }

  @PostMapping(value = "/users/user_info")
  public ResponseEntity<ResponseDataDto>login(HttpServletRequest request, HttpServletResponse response){
    String handle = (String)request.getAttribute(Constants.USER_HANDLE);
    return ResponseEntity.ok(new ResponseDataDto(HttpStatus.OK.value(), handle));
  }
}
