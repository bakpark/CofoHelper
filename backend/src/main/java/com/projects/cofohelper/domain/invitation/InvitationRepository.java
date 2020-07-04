package com.projects.cofohelper.domain.invitation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projects.cofohelper.domain.group.Group;
import com.projects.cofohelper.domain.user.User;

public interface InvitationRepository extends JpaRepository<Invitation, Long>{
	public List<Invitation> findByGroupAndInvited(Group group, User invited);
}
