package com.projects.cofohelper.controller.problem;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.projects.cofohelper.domain.problem.Problem;
import com.projects.cofohelper.dto.request.ProblemRegisterDto;
import com.projects.cofohelper.dto.response.ResponseDataDto;
import com.projects.cofohelper.service.problem.ProblemService;

@RestController
public class ProblemController {

	@Autowired
	ProblemService problemService;
	
	@PostMapping(value = "/problems")
	public ResponseEntity<ResponseDataDto> register(@RequestBody ProblemRegisterDto requestDto) throws Exception{
		Problem problem = problemService.register(requestDto);
		return ResponseEntity.ok()
				.body(new ResponseDataDto(HttpStatus.OK.value(), problem));
	}
	@GetMapping(value = "/problems", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> getProblemHtml(String problemName) {
		return ResponseEntity.ok()
				.body(problemService.getHtml(problemName));
	}
}
