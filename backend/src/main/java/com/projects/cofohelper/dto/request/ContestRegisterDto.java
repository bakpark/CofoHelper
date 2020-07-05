package com.projects.cofohelper.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class ContestRegisterDto {
	private String contestName;
	private Long groupId;
}
