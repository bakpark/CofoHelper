package com.projects.cofohelper.service.invitation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projects.cofohelper.domain.group.Group;
import com.projects.cofohelper.domain.group.GroupRepository;
import com.projects.cofohelper.domain.invitation.Invitation;
import com.projects.cofohelper.domain.invitation.InvitationRepository;
import com.projects.cofohelper.domain.partyinfo.PartyInfo;
import com.projects.cofohelper.domain.partyinfo.PartyInfoRepository;
import com.projects.cofohelper.domain.user.User;
import com.projects.cofohelper.domain.user.UserRepository;
import com.projects.cofohelper.dto.request.InvitationAcceptDto;
import com.projects.cofohelper.dto.request.InvitationRegisterDto;
import com.projects.cofohelper.exception.InvalidParameterException;
import com.projects.cofohelper.exception.UnAuthorizedException;
import com.projects.cofohelper.exception.alreadyexist.InvitationAlreadyExistException;
import com.projects.cofohelper.exception.notfound.HandleNotFoundException;

@Service
public class InvitationService {

	@Autowired 
	UserRepository userRepo;
	@Autowired
	GroupRepository groupRepo;
	@Autowired
	InvitationRepository invitationRepo;
	@Autowired
	PartyInfoRepository partyInfoRepo;
	
	public Invitation register(InvitationRegisterDto requestDto, String inviterHandle) {
		if(userRepo.findByHandle(requestDto.getInvitedHandle()) == null) 
			throw new HandleNotFoundException("Not found handle:"+requestDto.getInvitedHandle());
		User inviter = userRepo.findByHandle(inviterHandle);
		User invited = userRepo.findByHandle(requestDto.getInvitedHandle());
		Group group = groupRepo.getOne(requestDto.getGroupId());
		
		// groupId가 잘못 된 경우
		if(group == null)
			throw new InvalidParameterException("groupId is wrong");
		if(!isParticipantOf(group, inviter))
			throw new UnAuthorizedException("Unauthorized invite to group <"+group.getGroupName()+">");
		if(alreadyExist(group,invited))
			throw new InvitationAlreadyExistException(invited.getHandle()+" already invited to group <"+group.getGroupName()+">");
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
	

	public void delete(Long invitationId, String requesterHandle) {
		Invitation invitation = invitationRepo.getOne(invitationId);
		if(invitation == null)
			throw new InvalidParameterException("invationId is wrong");
		User requester = userRepo.findByHandle(requesterHandle);
		if(!requester.equals(invitation.getInvited()) && !requester.equals(invitation.getInvited()))
			throw new UnAuthorizedException("UnAuthorized to delete for this invitation");
		deleteInvitation(invitation);
	}
	
	public void accept(InvitationAcceptDto requestDto, String requesterHandle) {
		Invitation invitation = invitationRepo.getOne(requestDto.getInvitationId());
		if(invitation == null)
			throw new InvalidParameterException("invationId is wrong");
		User requester = userRepo.findByHandle(requesterHandle);
		User invited = invitation.getInvited();
		Group group = invitation.getGroup();
		if(!invited.equals(requester))
			throw new UnAuthorizedException("UnAuthorized to accept for this invitation");
		addConnectionUserAndGroup(invited, group);
		deleteInvitation(invitation);
	}
	private boolean alreadyExist(Group group, User invited) {
		List<Invitation> list = invitationRepo.findByGroupAndInvited(group, invited);
		return list.size()>0;
	}
	private boolean isParticipantOf(Group group, User user) {
		List<PartyInfo> list = partyInfoRepo.findByGroupAndUser(group, user);
		return list.size()>0;
	}

	private void deleteInvitation(Invitation invitation) {
		invitation.getInvited().removeInvitation(invitation);
		invitationRepo.delete(invitation);
	}
	

	private void addConnectionUserAndGroup(User invited, Group group) {
		PartyInfo partyInfo = PartyInfo.builder()
								.user(invited)
								.group(group)
								.build();
		partyInfoRepo.save(partyInfo);
		invited.addPartyInfo(partyInfo);
		group.addPartyInfo(partyInfo);
	}
	
	
	// for test
	public List<Invitation> getAll(){
		return invitationRepo.findAll();
	}

	
}
