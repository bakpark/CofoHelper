package com.projects.cofohelper.service.invitation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projects.cofohelper.domain.group.Group;
import com.projects.cofohelper.domain.group.GroupRepository;
import com.projects.cofohelper.domain.invitation.Invitation;
import com.projects.cofohelper.domain.invitation.InvitationRepository;
import com.projects.cofohelper.domain.user.User;
import com.projects.cofohelper.domain.user.UserRepository;
import com.projects.cofohelper.dto.request.InvitationRegisterDto;
import com.projects.cofohelper.exception.UserException;

@Service
public class InvitationService {

	@Autowired 
	UserRepository userRepo;
	@Autowired
	GroupRepository groupRepo;
	@Autowired
	InvitationRepository invitationRepo;
	
	public Invitation register(InvitationRegisterDto requestDto, String inviterHandle) {
		if(userRepo.findByHandle(requestDto.getInvitedHandle()) == null) 
			throw new UserException("Not found user handle:"+requestDto.getInvitedHandle());
		User inviter = userRepo.findByHandle(inviterHandle);
		User invited = userRepo.findByHandle(requestDto.getInvitedHandle());
		Group group = groupRepo.getOne(requestDto.getGroupId());
		
		Invitation invitation = Invitation.builder()
									.inviter(inviter)
									.invited(invited)
									.group(group)
									.build();
		invitationRepo.save(invitation);
		invited.addInvitation(invitation);
		group.addInvitation(invitation);
		
		return invitation;
	}
	
	public List<Invitation> getAll(String handle){
		User user = userRepo.findByHandle(handle);
		return user.getInvitations();
	}
}
