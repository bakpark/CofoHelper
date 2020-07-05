package com.projects.cofohelper.domain.contestprobleminfo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projects.cofohelper.domain.contest.Contest;

public interface ContestProblemInfoRepository extends JpaRepository<ContestProblemInfo, Long>{
	List<ContestProblemInfo> findAllByContest(Contest contest);
}
