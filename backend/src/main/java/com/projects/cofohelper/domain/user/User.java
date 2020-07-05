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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projects.cofohelper.domain.invitation.Invitation;
import com.projects.cofohelper.domain.partyinfo.PartyInfo;

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
  
  @JsonIgnore
  public String password;
  
  @JsonBackReference
  @OneToMany(mappedBy = "user")
  private List<PartyInfo> parties;
  
  @JsonBackReference
  @OneToMany(mappedBy = "invited")
  private List<Invitation> invitations = new ArrayList<Invitation>();
  
  public void addPartyInfo(PartyInfo info) {
	  if(parties == null)
		  parties = new ArrayList<PartyInfo>();
	  parties.add(info);
  }
  public void addInvitation(Invitation invitation) {
	  if(invitations == null)
		  invitations = new ArrayList<Invitation>();
	  invitations.add(invitation);
  }
  public void removeInvitation(Invitation invitation) {
	  invitations.remove(invitation);
  }
  
  public String toString() {
	  return "userId:"+userId+"handle"+handle;
  }
}
