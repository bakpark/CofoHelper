package com.projects.cofohelper.Dto.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequestDto {
	private String handle;
	private String password;
}
