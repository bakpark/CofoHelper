package com.projects.cofohelper.service.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projects.cofohelper.domain.group.Group;
import com.projects.cofohelper.domain.group.GroupRepository;
import com.projects.cofohelper.domain.partyinfo.PartyInfo;
import com.projects.cofohelper.domain.partyinfo.PartyInfoRepository;
import com.projects.cofohelper.domain.user.User;
import com.projects.cofohelper.domain.user.UserRepository;
import com.projects.cofohelper.dto.request.GroupRegisterRequestDto;
import com.projects.cofohelper.dto.response.GroupRegisterResponseDto;
import com.projects.cofohelper.exception.UserException;

@Service
public class GroupService {

	@Autowired
	UserRepository userRepo;
	@Autowired
	GroupRepository groupRepo;
	@Autowired
	PartyInfoRepository partyInfoRepo;

	public GroupRegisterResponseDto register(GroupRegisterRequestDto request, String makerHandle) {
		GroupRegisterResponseDto responseDto = new GroupRegisterResponseDto();
		for(String handle : request.users) {
			User user = userRepo.findByHandle(handle);
			if(user == null) throw new UserException("handle not found :" + handle);
			// 요부분은 아직 invitation구현중이라 안한건가
			else responseDto.insertUser(user);
		}
		User maker = userRepo.findByHandle(makerHandle);
		Group group = new Group(request.groupName);
		group = groupRepo.save(group);
		PartyInfo conn = PartyInfo.builder()
							.group(group)
							.user(maker)
							.build();
		conn = partyInfoRepo.save(conn);
		maker.addPartyInfo(conn);
		group.addPartyInfo(conn);
		return responseDto;
	}


}
