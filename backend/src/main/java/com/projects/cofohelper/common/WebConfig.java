package com.projects.cofohelper.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Autowired
  private JwtInterceptor jwtInterceptor;

  public static final String[] includePaths = {"/user/user_info", "api/**"};
  public static final String[] excludePaths = {"user/login/**", "user/join/**"};

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(jwtInterceptor)
      .addPathPatterns(includePaths)
      .excludePathPatterns(excludePaths);
  }
}
