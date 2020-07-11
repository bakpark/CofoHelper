package com.projects.cofohelper.dto.response;

import java.util.ArrayList;

import com.projects.cofohelper.domain.user.User;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class GroupRegisterResponseDto {
	public ArrayList<User> users = new ArrayList<User>();
	public void insertUser(User user) {
		users.add(user);
	}
}
