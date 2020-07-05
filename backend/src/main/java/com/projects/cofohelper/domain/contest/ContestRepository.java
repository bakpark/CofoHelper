package com.projects.cofohelper.domain.contest;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projects.cofohelper.domain.group.Group;

public interface ContestRepository extends JpaRepository<Contest, Long> {
	public Contest findByGroupAndContestName(Group group, String contestName);
	public List<Contest> findByGroup(Group group);
}
