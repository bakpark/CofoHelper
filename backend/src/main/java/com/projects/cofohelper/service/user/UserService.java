package com.projects.cofohelper.service.user;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.projects.cofohelper.domain.group.Group;
import com.projects.cofohelper.domain.invitation.Invitation;
import com.projects.cofohelper.domain.partyinfo.PartyInfo;
import com.projects.cofohelper.domain.user.User;
import com.projects.cofohelper.domain.user.UserRepository;
import com.projects.cofohelper.dto.request.UserLoginRequestDto;
import com.projects.cofohelper.dto.request.UserRegisterRequestDto;
import com.projects.cofohelper.exception.alreadyexist.HandleAlreadyExistException;
import com.projects.cofohelper.exception.notfound.HandleNotFoundException;

@Service
public class UserService {
  @Autowired
  UserRepository userRepository;
  private PasswordEncoder encoder = new BCryptPasswordEncoder();

  public String getHandle(Long userId){
    User user = userRepository.findById(userId).get();
    if(user == null){
      throw new HandleNotFoundException("아이디를 찾을 수 없습니다");
    }
    return user.getHandle();
  }

  public User register(UserRegisterRequestDto request) {
	  if(isExistHandle(request.getHandle()))
		  throw new HandleAlreadyExistException("Already Exist");
	  User user = User.builder()
			  .handle(request.getHandle())
			  .password(encoder.encode(request.getPassword()))
			  .build();
	  return userRepository.save(user);
  }

  public User login(UserLoginRequestDto request) {
	  User user = userRepository.findByHandle(request.getHandle());
	  if(user == null) {
		  throw new HandleNotFoundException("Wrong Handle");
	  } else if(!encoder.matches(request.getPassword(), user.getPassword())) {
		  throw new InvalidParameterException("Wrong Password");
	  } else {
		  return user;
	  }
  }
  
  public List<Invitation> getInvitations(String handle){
	  User user = userRepository.findByHandle(handle);
	  return user.getInvitations();
  }
  
  public List<Group> getGroups(String handle){
	  User user = userRepository.findByHandle(handle);
	  ArrayList<Group> groups = new ArrayList<Group>();
	  for(PartyInfo info : user.getParties()) {
		  groups.add(info.getGroup());
	  }
	  return groups;
  }
  
  // for test
  public List<User> getAll(){
	  return userRepository.findAll();
  }


  private boolean isExistHandle(String handle) {
	  return userRepository.findByHandle(handle) != null;
  }
  

}
