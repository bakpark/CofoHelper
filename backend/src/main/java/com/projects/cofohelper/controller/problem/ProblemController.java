package com.projects.cofohelper.controller.problem;

import java.io.IOException;
import java.net.URISyntaxException;

import com.projects.cofohelper.common.Constants;
import com.projects.cofohelper.dto.request.ContestProblemRegisterDto;
import com.projects.cofohelper.service.contest.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projects.cofohelper.domain.problem.Problem;
import com.projects.cofohelper.dto.request.ProblemRegisterDto;
import com.projects.cofohelper.dto.response.ResponseDataDto;
import com.projects.cofohelper.service.problem.ProblemService;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ProblemController {

	@Autowired
	ProblemService problemService;

	@Autowired
	ContestService contestService;

	@PostMapping(value = "/api/problems")
	public ResponseEntity<ResponseDataDto> register(@RequestBody ProblemRegisterDto requestDto) throws Exception {
		Problem problem = problemService.register(requestDto);
		return ResponseEntity.ok().body(new ResponseDataDto(HttpStatus.OK.value(), problem));
	}

	@GetMapping(value = "/api/problems", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> getProblemHtml(String problemName) {
		return ResponseEntity.ok().body(problemService.getHtml(problemName));
	}

	@PostMapping(value = "/api/contests/{contestId}/problems")
	public ResponseEntity<ResponseDataDto> register(@RequestBody ContestProblemRegisterDto registerDto,
			@PathVariable Long contestId, HttpServletRequest request) throws Exception {
		if (!problemService.isExist(registerDto.getProblemName()))
			problemService.register(new ProblemRegisterDto(registerDto.getProblemName()));
		String requesterHandle = (String) request.getAttribute(Constants.USER_HANDLE);
		return ResponseEntity.ok().body(new ResponseDataDto(HttpStatus.OK.value(),
				contestService.addProblem(contestId, registerDto, requesterHandle)));
	}

	@GetMapping(value = "/api/contests/{contestId}/problems")
	public ResponseEntity<ResponseDataDto> getProblems(@PathVariable Long contestId, HttpServletRequest request) {
		String requesterHandle = (String) request.getAttribute(Constants.USER_HANDLE);
		return ResponseEntity.ok().body(
				new ResponseDataDto(HttpStatus.OK.value(), contestService.getProblems(contestId, requesterHandle)));
	}
}
