package com.projects.cofohelper.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContestProblemRegisterDto {
	private Long contestId;
	String problemName;
}
