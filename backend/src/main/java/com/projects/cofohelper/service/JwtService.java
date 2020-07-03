package com.projects.cofohelper.service;

import com.projects.cofohelper.common.Constants;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

  Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
  public String create(String userId){
    return Jwts.builder()
            .claim(Constants.USER_ID, userId)
            .setExpiration(new Date(System.currentTimeMillis() + (60*60*24) * 1000))
            .signWith(key)
            .compact();
  }
  public boolean valid(String token) {
    try{
      Date expiration = Jwts.parserBuilder()
                          .setSigningKey(key)
                          .build()
                          .parseClaimsJws(token)
                          .getBody()
                          .getExpiration();
      Date now = new Date();
      if(now.after(expiration)){
        return false;
      }
      return true;
    }catch(Exception e){
      return false;
    }
  }
  public String getHandle(String token){
    try{
      String handle = (String)Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .get(Constants.USER_ID);
      return handle;
    } catch(Exception e){
      return null;
    }
  }
}
