package com.projects.cofohelper.controller.contest;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projects.cofohelper.common.Constants;
import com.projects.cofohelper.dto.request.ContestProblemRegisterDto;
import com.projects.cofohelper.dto.request.ContestRegisterDto;
import com.projects.cofohelper.dto.request.ProblemRegisterDto;
import com.projects.cofohelper.dto.response.ResponseDataDto;
import com.projects.cofohelper.service.contest.ContestService;
import com.projects.cofohelper.service.problem.ProblemService;

@RestController
public class ContestController {

	@Autowired
	ContestService contestService;
	@Autowired
	ProblemService problemService;
	
	@PostMapping(value = "/contests")
	public ResponseEntity<ResponseDataDto> register(ContestRegisterDto registerDto, HttpServletRequest request){
		String requesterHandle = (String)request.getAttribute(Constants.USER_HANDLE);
		return ResponseEntity.ok()
				.body(new ResponseDataDto(HttpStatus.OK.value(), contestService.register(registerDto, requesterHandle)));
	}
	
	@PostMapping(value = "contest/problem")
	public ResponseEntity<ResponseDataDto> register(ContestProblemRegisterDto registerDto, HttpServletRequest request) throws Exception {
		if(!problemService.isExist(registerDto.getProblemName()))
			problemService.register(new ProblemRegisterDto(registerDto.getProblemName()));
		String requesterHandle = (String)request.getAttribute(Constants.USER_HANDLE);
		return ResponseEntity.ok()
				.body(new ResponseDataDto(HttpStatus.OK.value(), contestService.addProblem(registerDto, requesterHandle)));
	}
	
}
