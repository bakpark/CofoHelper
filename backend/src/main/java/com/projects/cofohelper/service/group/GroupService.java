package com.projects.cofohelper.service.group;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projects.cofohelper.domain.group.Group;
import com.projects.cofohelper.domain.group.GroupRepository;
import com.projects.cofohelper.domain.invitation.Invitation;
import com.projects.cofohelper.domain.partyinfo.PartyInfo;
import com.projects.cofohelper.domain.partyinfo.PartyInfoRepository;
import com.projects.cofohelper.domain.user.User;
import com.projects.cofohelper.domain.user.UserRepository;
import com.projects.cofohelper.dto.request.GroupRegisterRequestDto;
import com.projects.cofohelper.exception.UnAuthorizedException;
import com.projects.cofohelper.exception.alreadyexist.GroupAlreadyExistException;
import com.projects.cofohelper.exception.notfound.GroupNotFoundException;

@Service
public class GroupService {

	@Autowired
	UserRepository userRepo;
	@Autowired
	GroupRepository groupRepo;
	@Autowired
	PartyInfoRepository partyInfoRepo;

	public Group getGroup(Long id){
	  Group group = groupRepo.getOne(id);
	  if(group == null)
	    throw new GroupNotFoundException("Group Not Found groupId:" + id);
	  return group;
  }
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

	public List<Invitation> getInvitations(Long groupId, String requesterHandle) {
		Group group = groupRepo.getOne(groupId);
		User requester = userRepo.findByHandle(requesterHandle);
		if(group == null)
			throw new GroupNotFoundException("Group not found groupId:"+groupId);
		boolean inGroup = false;
		for(PartyInfo info : group.getParties()) {
			if(requester.equals(info.getUser())) {
				inGroup = true;
				break;
			}
		}
		if(!inGroup)
			throw new UnAuthorizedException("UnAuthorized to group :"+groupId);
		return group.getInvitations();
	}


}
