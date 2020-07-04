package com.projects.cofohelper.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class InvitationRegisterDto {
	
//	public Long invitedId;
	public String invitedHandle;
	public Long groupId;

}
