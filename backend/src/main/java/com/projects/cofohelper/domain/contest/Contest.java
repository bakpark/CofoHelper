package com.projects.cofohelper.domain.contest;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.projects.cofohelper.domain.contestprobleminfo.ContestProblemInfo;
import com.projects.cofohelper.domain.group.Group;

import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Contest {
	@Id
	@GeneratedValue
	private Long contestId;
	
	@ManyToOne(targetEntity = Group.class)
	private Group group;
	
	@OneToMany(mappedBy = "contest")
	private List<ContestProblemInfo> problemInfos;
	
	public void insertProblemInfos(ContestProblemInfo problemInfo) {
		if(problemInfos == null)
			problemInfos = new ArrayList<ContestProblemInfo>();
		problemInfos.add(problemInfo);
	}
}
