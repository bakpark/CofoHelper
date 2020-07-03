package com.projects.cofohelper.service.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projects.cofohelper.domain.group.GroupRepository;
import com.projects.cofohelper.domain.user.User;
import com.projects.cofohelper.domain.user.UserRepository;
import com.projects.cofohelper.dto.request.GroupRegisterRequestDto;
import com.projects.cofohelper.dto.response.GroupRegisterResponseDto;
import com.projects.cofohelper.exception.UserException;

@Service
public class GroupService {
	
	@Autowired
	UserRepository userRepo;
	@Autowired
	GroupRepository groupRepo;
	
	public GroupRegisterResponseDto register(GroupRegisterRequestDto request) {
		GroupRegisterResponseDto responseDto = new GroupRegisterResponseDto();
		for(String handle : request.users) {
//			System.out.println("in group service ok user : "+user.getHandle() + user.getUserId());
			User user = userRepo.findByHandle(handle);
			if(user == null) throw new UserException("handle not found :" + handle);
			else responseDto.insertUser(user);
		}
		return responseDto;
	}
	
	
}
