package com.projects.cofohelper.service.invitation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projects.cofohelper.domain.group.Group;
import com.projects.cofohelper.domain.group.GroupRepository;
import com.projects.cofohelper.domain.invitation.Invitation;
import com.projects.cofohelper.domain.invitation.InvitationRepository;
import com.projects.cofohelper.domain.user.User;
import com.projects.cofohelper.domain.user.UserRepository;
import com.projects.cofohelper.dto.InvitationRegisterDto;

@Service
public class InvitationService {

	@Autowired 
	UserRepository userRepo;
	@Autowired
	GroupRepository groupRepo;
	@Autowired
	InvitationRepository invitationRepo;
	
	public Invitation register(InvitationRegisterDto request) {
		User inviter = userRepo.getOne(request.getInviterId());
		User invited = userRepo.getOne(request.getInviterId());
		Group group = groupRepo.getOne(request.getGroupId());
		
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
}
