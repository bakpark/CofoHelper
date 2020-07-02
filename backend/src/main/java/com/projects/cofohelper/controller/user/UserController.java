package com.projects.cofohelper.controller.user;

import com.projects.cofohelper.domain.user.User;
import com.projects.cofohelper.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
  // TODO: 2020-07-02 join,login,getUserInfo 작성해야 함
  @Autowired
  UserService userService;

  @RequestMapping(value = "/users", method = RequestMethod.POST)
  public String join(@RequestBody User user){
    return userService.join(user) + "";
  }
}
