package com.projects.cofohelper.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.projects.cofohelper.domain.user.User;
import com.projects.cofohelper.domain.user.UserRepository;
import com.projects.cofohelper.dto.request.UserLoginRequestDto;
import com.projects.cofohelper.dto.request.UserRegisterRequestDto;
import com.projects.cofohelper.exception.UserException;

@Service
public class UserService {
  @Autowired
  UserRepository userRepository;
  private PasswordEncoder encoder = new BCryptPasswordEncoder();

  public String getHandle(Long userId){
    User user = userRepository.findById(userId).get();
    if(user == null){
      throw new UserException("아이디를 찾을 수 없습니다");
    }
    return user.getHandle();
  }

  public User register(UserRegisterRequestDto request) {
	  if(isExistHandle(request.getHandle()))
		  throw new UserException("Already Exist");
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
	  } else if(!encoder.matches(request.getPassword(), user.getPassword())) {
		  throw new UserException("Wrong Password");
	  } else {
		  return user;
	  }
  }


  private boolean isExistHandle(String handle) {
	  return userRepository.findByHandle(handle) != null;
  }
}
