package com.projects.cofohelper.domain.problem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long>{
	public List<Problem> findByName(String name);
}
