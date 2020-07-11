package com.projects.cofohelper.domain.partyinfo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projects.cofohelper.domain.group.Group;
import com.projects.cofohelper.domain.user.User;

public interface PartyInfoRepository extends JpaRepository<PartyInfo, Long> {
	PartyInfo findByGroupAndUser(Group group, User user);
	List<PartyInfo> findByGroup(Group group);
}
