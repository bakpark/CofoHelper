package com.projects.cofohelper.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class UserRegisterResponseDto {
	private Long id;
	private String handle;
}
