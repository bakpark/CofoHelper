package com.projects.cofohelper.domain.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.projects.cofohelper.domain.partyinfo.PartyInfo;
import com.projects.cofohelper.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "USER_ID")
  public Long userId;

  public String handle;
  public String password;
  
  @JsonBackReference
  @OneToMany(mappedBy = "user")
  @Builder.Default
  private List<PartyInfo> parties = new ArrayList<PartyInfo>();
  
  public void addPartyInfo(PartyInfo info) {
	  parties.add(info);
  }
  
  public UserDto toDto() {
	  return UserDto.builder()
			  .userId(userId)
			  .handle(handle)
			  .build();
  }
}
