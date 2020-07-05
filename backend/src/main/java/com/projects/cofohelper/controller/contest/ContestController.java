package com.projects.cofohelper.controller.contest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	@PostMapping(value = "/api/contests")
	public ResponseEntity<ResponseDataDto> register(@RequestBody ContestRegisterDto registerDto, HttpServletRequest request){
		String requesterHandle = (String)request.getAttribute(Constants.USER_HANDLE);
		return ResponseEntity.ok()
				.body(new ResponseDataDto(HttpStatus.OK.value(), contestService.register(registerDto, requesterHandle)));
	}

	@PostMapping(value = "/api/contest/problems")
	public ResponseEntity<ResponseDataDto> register(@RequestBody ContestProblemRegisterDto registerDto, HttpServletRequest request) throws Exception {
		if(!problemService.isExist(registerDto.getProblemName()))
			problemService.register(new ProblemRegisterDto(registerDto.getProblemName()));
		String requesterHandle = (String)request.getAttribute(Constants.USER_HANDLE);
		return ResponseEntity.ok()
				.body(new ResponseDataDto(HttpStatus.OK.value(), contestService.addProblem(registerDto, requesterHandle)));
	}

	@GetMapping(value = "/api/contest/problems")
	public ResponseEntity<ResponseDataDto> getProblems(Long contestId, HttpServletRequest request){
		String requesterHandle = (String)request.getAttribute(Constants.USER_HANDLE);
		return ResponseEntity.ok()
				.body(new ResponseDataDto(HttpStatus.OK.value(), contestService.getProblems(contestId, requesterHandle)));
	}
}
