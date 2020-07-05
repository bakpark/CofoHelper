package com.projects.cofohelper.service.contest;

import java.util.ArrayList;
import java.util.List;

import com.projects.cofohelper.service.group.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projects.cofohelper.domain.contest.Contest;
import com.projects.cofohelper.domain.contest.ContestRepository;
import com.projects.cofohelper.domain.contestprobleminfo.ContestProblemInfo;
import com.projects.cofohelper.domain.contestprobleminfo.ContestProblemInfoRepository;
import com.projects.cofohelper.domain.group.Group;
import com.projects.cofohelper.domain.group.GroupRepository;
import com.projects.cofohelper.domain.partyinfo.PartyInfo;
import com.projects.cofohelper.domain.problem.Problem;
import com.projects.cofohelper.domain.problem.ProblemRepository;
import com.projects.cofohelper.domain.user.User;
import com.projects.cofohelper.domain.user.UserRepository;
import com.projects.cofohelper.dto.request.ContestProblemRegisterDto;
import com.projects.cofohelper.dto.request.ContestRegisterDto;
import com.projects.cofohelper.exception.UnAuthorizedException;
import com.projects.cofohelper.exception.alreadyexist.ContestAlreadyExistException;
import com.projects.cofohelper.exception.alreadyexist.ProblemAlreadyExistException;
import com.projects.cofohelper.exception.notfound.ContestNotFoundException;
import com.projects.cofohelper.exception.notfound.GroupNotFoundException;

@Service
public class ContestService {

	@Autowired
	UserRepository userRepo;
	@Autowired
  GroupService groupService;
	@Autowired
	GroupRepository groupRepo;
	@Autowired
	ContestRepository contestRepo;
	@Autowired
	ProblemRepository problemRepo;
	@Autowired
	ContestProblemInfoRepository problemInfoRepo;

	public List<Contest> getContests(Long groupId){
	  Group group = groupService.getGroup(groupId);
    return group.getContests();
  }

	public Contest register(Long groupId, ContestRegisterDto registerDto, String requesterHandle) {
		System.out.println("requester:"+requesterHandle+" groupId:"+groupId);
		User requester = userRepo.findByHandle(requesterHandle);
		Group group = groupService.getGroup(groupId);
		if (!isPartyIn(requester, group))
			throw new UnAuthorizedException("Unauthorized to make contest for group:" + group.getGroupId());
		if (contestRepo.findByGroupAndContestName(group, registerDto.getContestName()) != null)
			throw new ContestAlreadyExistException(
					"ContestName already exist in group name:" + registerDto.getContestName());

		Contest contest = new Contest(registerDto.getContestName(), group);
		contestRepo.save(contest);
		group.addContest(contest);

		return contest;
	}

	public Problem addProblem(Long contestId, ContestProblemRegisterDto registerDto, String requesterHandle) {
		User requester = userRepo.findByHandle(requesterHandle);
		Contest contest = contestRepo.getOne(contestId);
		if (contest == null)
			throw new GroupNotFoundException("Contest not found contestId:" + contestId);
		Group group = contest.getGroup();
		if (!isPartyIn(requester, group))
			throw new UnAuthorizedException("Unauthorized to add problem for contest:" + contest.getContestId());
		if (isContestIn(registerDto.getProblemName(), contest))
			throw new ProblemAlreadyExistException(
					"Problem already exist in contest problemName:" + registerDto.getProblemName());

		Problem problem = problemRepo.findByName(registerDto.getProblemName());
		ContestProblemInfo problemInfo = new ContestProblemInfo(contest, problem);
		problemInfoRepo.save(problemInfo);
		contest.insertProblemInfos(problemInfo);
		return problem;
	}
	public List<Problem> getProblems(Long contestId, String requesterHandle) {
		User requester = userRepo.findByHandle(requesterHandle);
		Contest contest = contestRepo.getOne(contestId);
		if(contest == null)
			throw new ContestNotFoundException("Contest Not Found contestId:"+contestId);
		Group group = contest.getGroup();
		if(!isPartyIn(requester, group))
			throw new UnAuthorizedException("Unauthorized to get problems for contest:"+contestId);
		List<ContestProblemInfo> problemInfos = problemInfoRepo.findAllByContest(contest);
		List<Problem> problems = new ArrayList<>();
		for(ContestProblemInfo info : problemInfos) {
			problems.add(info.getProblem());
		}
		return problems;
	}

	private boolean isPartyIn(User requester, Group group) {
		for (PartyInfo partyInfo : group.getParties()) {
			if (partyInfo.getUser().equals(requester)) {
				return true;
			}
		}
		return false;
	}

	private boolean isContestIn(String problemName, Contest contest) {
		for (ContestProblemInfo problemInfo : contest.getProblemInfos()) {
			if (problemInfo.getProblem().getName().equals(problemName)) {
				return true;
			}
		}
		return false;
	}


}
