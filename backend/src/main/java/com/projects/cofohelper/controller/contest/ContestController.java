package com.projects.cofohelper.controller.contest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	@PostMapping(value = "/api/groups/{groupId}/contests")
	public ResponseEntity<ResponseDataDto> register(@RequestBody ContestRegisterDto registerDto,
                                                  @PathVariable Long groupId,
                                                  HttpServletRequest request){
		String requesterHandle = (String)request.getAttribute(Constants.USER_HANDLE);
		return ResponseEntity.ok()
				.body(new ResponseDataDto(HttpStatus.OK.value(), contestService.register(groupId, registerDto, requesterHandle)));
	}

	@GetMapping(value="/api/groups/{groupId}/contests")
	public ResponseEntity<ResponseDataDto> getContests(@PathVariable Long groupId) {
		return ResponseEntity.ok()
				.body(new ResponseDataDto(HttpStatus.OK.value(), contestService.getContests(groupId)));
  }

  @GetMapping(value="/api/contests/{contestId}")
  public ResponseEntity<ResponseDataDto> getContest(@PathVariable Long contestId){
	  return ResponseEntity.ok().body(new ResponseDataDto(HttpStatus.OK.value(), contestService.getContest(contestId)));
  }

  @GetMapping(value="/ap/groups/{groupId}/contests_ongoing")
  public ResponseEntity<ResponseDataDto> getContestsOngoing(@PathVariable Long groupId){
    return ResponseEntity.ok()
      .body(new ResponseDataDto(HttpStatus.OK.value(), contestService.getContestsOngoing(groupId)));
  }

  @DeleteMapping(value="/api/contests/{contestId}")
  public ResponseEntity<ResponseDataDto> deleteContest(@PathVariable Long contestId){
	  return ResponseEntity.ok()
      .body(new ResponseDataDto(HttpStatus.OK.value(), contestService.deleteContest(contestId)));
  }

}
