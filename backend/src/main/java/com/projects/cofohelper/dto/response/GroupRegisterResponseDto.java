package com.projects.cofohelper.dto.response;

import java.util.ArrayList;

import com.projects.cofohelper.domain.user.User;
import com.projects.cofohelper.dto.UserDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class GroupRegisterResponseDto {
	public ArrayList<UserDto> users = new ArrayList<UserDto>();
	public void insertUser(User user) {
		users.add(user.toDto());
	}
}
