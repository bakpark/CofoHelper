package com.projects.cofohelper.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InvitationRegisterDto {
	
	public String invitedHandle;
	public Long groupId;

}
