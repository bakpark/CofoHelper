package com.projects.cofohelper.service.group;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projects.cofohelper.domain.group.Group;
import com.projects.cofohelper.domain.group.GroupRepository;
import com.projects.cofohelper.domain.partyinfo.PartyInfo;
import com.projects.cofohelper.domain.partyinfo.PartyInfoRepository;
import com.projects.cofohelper.domain.user.User;
import com.projects.cofohelper.domain.user.UserRepository;
import com.projects.cofohelper.dto.request.GroupRegisterRequestDto;
import com.projects.cofohelper.exception.GroupAlreadyExistException;

@Service
public class GroupService {

	@Autowired
	UserRepository userRepo;
	@Autowired
	GroupRepository groupRepo;
	@Autowired
	PartyInfoRepository partyInfoRepo;

	public Group register(GroupRegisterRequestDto request, String makerHandle) {
		if(groupRepo.findByGroupName(request.getGroupName()) != null) 
			throw new GroupAlreadyExistException("Already exist group name:"+request.groupName);
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
		return group;
	}
	
	// for test
	public List<Group> getAll(){
		return groupRepo.findAll();
	}


}
