package com.projects.cofohelper.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class UserDto {
	private Long userId;
	private String handle;
	public String toString() {
		return "in userDto userId:"+userId+" userHandle:"+handle;
	}
}
