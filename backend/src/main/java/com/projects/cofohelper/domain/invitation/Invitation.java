package com.projects.cofohelper.domain.invitation;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.projects.cofohelper.domain.group.Group;
import com.projects.cofohelper.domain.user.User;

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
public class Invitation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long invitationId;
	
	@ManyToOne(targetEntity = Group.class)
	Group group;
	
	@ManyToOne(targetEntity = User.class)
	User inviter;
	
	@ManyToOne(targetEntity = User.class)
	User invited;
}
