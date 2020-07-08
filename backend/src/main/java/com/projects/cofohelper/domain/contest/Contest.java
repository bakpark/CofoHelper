package com.projects.cofohelper.domain.contest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.projects.cofohelper.domain.contestprobleminfo.ContestProblemInfo;
import com.projects.cofohelper.domain.group.Group;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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

	@Column(nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  LocalDateTime endTime;

	@JsonBackReference
	@OneToMany(mappedBy = "contest", cascade= CascadeType.REMOVE)
	private List<ContestProblemInfo> problemInfos = new ArrayList<>();

	public void insertProblemInfos(ContestProblemInfo problemInfo) {
		if(problemInfos == null)
			problemInfos = new ArrayList<ContestProblemInfo>();
		problemInfos.add(problemInfo);
	}
}
