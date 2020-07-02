package com.projects.cofohelper.service.user;

import com.projects.cofohelper.domain.user.User;
import com.projects.cofohelper.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  @Autowired
  UserRepository userRepository;

  public Long join(User user){
    User checkUser = userRepository.findByUserName(user.getUserName());
    if(checkUser != null){
      throw new RuntimeException("유저 네임이 존재합니다");
    }
    return userRepository.save(user).getId();
  }

  // TODO: 2020-07-02 로그인서비스작성해야함
}
