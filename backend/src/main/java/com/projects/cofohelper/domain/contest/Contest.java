package com.projects.cofohelper.domain.contest;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.projects.cofohelper.domain.contestprobleminfo.ContestProblemInfo;
import com.projects.cofohelper.domain.group.Group;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Contest {
	@Id
	@GeneratedValue
	private Long contestId;

	private String contestName;

	@ManyToOne(targetEntity = Group.class)
	private Group group;

	public Contest(String name, Group group) {
		this.contestName = name;
		this.group = group;
		problemInfos = new ArrayList<>();
	}

	@JsonBackReference
	@OneToMany(mappedBy = "contest", cascade= CascadeType.REMOVE)
	private List<ContestProblemInfo> problemInfos = new ArrayList<>();

	public void insertProblemInfos(ContestProblemInfo problemInfo) {
		if(problemInfos == null)
			problemInfos = new ArrayList<ContestProblemInfo>();
		problemInfos.add(problemInfo);
	}
}
