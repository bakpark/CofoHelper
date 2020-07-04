package com.projects.cofohelper.domain.contestprobleminfo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.projects.cofohelper.domain.contest.Contest;
import com.projects.cofohelper.domain.problem.Problem;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class ContestProblemInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@ManyToOne(targetEntity = Problem.class)
	private Problem problem;
	
	@ManyToOne(targetEntity = Problem.class)
	private Contest contest;
}
