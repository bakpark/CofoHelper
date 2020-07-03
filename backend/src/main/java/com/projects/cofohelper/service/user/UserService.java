package com.projects.cofohelper.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.projects.cofohelper.Dto.Request.UserLoginRequestDto;
import com.projects.cofohelper.Dto.Request.UserRegisterRequestDto;
import com.projects.cofohelper.domain.user.User;
import com.projects.cofohelper.domain.user.UserRepository;
import com.projects.cofohelper.exception.UserException;

@Service
public class UserService {
  @Autowired
  UserRepository userRepository;
  private PasswordEncoder encoder = new BCryptPasswordEncoder();

  public Long join(User user){
    User checkUser = userRepository.findByHandle(user.getHandle());
    if(checkUser != null){
      throw new RuntimeException("유저 네임이 존재합니다");
    }
    return userRepository.save(user).getId();
  }

  public boolean isExistHandle(String handle) {
	  return userRepository.findByHandle(handle) != null;
  }
  public User register(UserRegisterRequestDto request) {
	  if(isExistHandle(request.getHandle()))
		  throw new UserException("Already Exist");
	  System.out.println("prePassword input:" + request.getPassword());
	  User user = User.builder()
			  .handle(request.getHandle())
			  .password(encoder.encode(request.getPassword()))
			  .build();
	  return userRepository.save(user);
  }
  
  public User login(UserLoginRequestDto request) {
	  User user = userRepository.findByHandle(request.getHandle());
	  if(user == null) {
		  throw new UserException("Wrong Handle");
	  } else if(encoder.matches(request.getPassword(), user.getPassword())) {
		  System.out.println("left:"+ request.getPassword()+ " right:"+ user.getPassword());
		  System.out.println("left:"+ encoder.encode(request.getPassword()) + " right:"+ user.getPassword());
		  throw new UserException("Wrong Password");
	  } else {
		  return user;
	  }
  }
}
