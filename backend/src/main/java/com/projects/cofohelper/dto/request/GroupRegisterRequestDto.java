package com.projects.cofohelper.dto.request;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GroupRegisterRequestDto {
	public String groupName;
	public ArrayList<String> users;
}
