package com.projects.cofohelper.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.projects.cofohelper.exception.InvalidTokenException;
import com.projects.cofohelper.service.JwtService;

@Component
public class JwtInterceptor implements HandlerInterceptor {
  @Autowired
  public JwtService jwtService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception{
    if("OPTIONS".equals(request.getMethod())){
      return true;
    }

    // token을 헤더에서 추출한다.
    // OPTIONS가 메소드이면 일단 return true; -> 왜?
    // token이 null이 아니고, token이 valid하다면 return true
    String token = request.getHeader(Constants.AUTHORIZATION);
    if(token != null && jwtService.valid(token)){
      request.setAttribute(Constants.USER_HANDLE, jwtService.getHandle(token));
      return true;
    }else{
      throw new InvalidTokenException("토큰이 유효하지 않습니다");
    }
  }
}
