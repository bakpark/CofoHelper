package com.projects.cofohelper.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class InvitationRegisterDto {
	
	public Long inviterId;
	public Long invitedId;
	public Long groupId;

}
