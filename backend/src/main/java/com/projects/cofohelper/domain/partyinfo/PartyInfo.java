package com.projects.cofohelper.domain.partyinfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.projects.cofohelper.domain.group.Group;
import com.projects.cofohelper.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class PartyInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	Long partyInfoId;
	
	@JsonManagedReference
	@ManyToOne(targetEntity = User.class)
	private User user;
	
	@JsonManagedReference
	@ManyToOne(targetEntity = Group.class)
	private Group group;
	
	
	
}
