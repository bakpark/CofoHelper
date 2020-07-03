package com.projects.cofohelper.controller.user;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
  // TODO: 2020-07-02 join,login,getUserInfo 작성해야 함
  @Autowired
  UserService userService;
  @Autowired
  JwtService jwtService;
  
  @PostMapping(value = "/users")
  public ResponseEntity<UserRegisterResponseDto> register(@RequestBody UserRegisterRequestDto request){
	  User user = userService.register(request);
	  UserRegisterResponseDto responseDto =
			  new UserRegisterResponseDto(user.getUserId(),user.getHandle());
			  
	  return new ResponseEntity<UserRegisterResponseDto>(responseDto, HttpStatus.OK);
  }
  
  @PostMapping(value = "/login")
  public ResponseEntity<UserLoginResponseDto>login(
		  @RequestBody UserLoginRequestDto request, HttpServletResponse response){
	  User user = userService.login(request);
	  UserLoginResponseDto responseDto = 
			  new UserLoginResponseDto(user.getUserId(), user.getHandle());
	  response.setHeader(Constants.AUTHORIZATION, jwtService.create(user.getHandle()));
	  return new ResponseEntity<UserLoginResponseDto>(responseDto, HttpStatus.OK);
  }
}
