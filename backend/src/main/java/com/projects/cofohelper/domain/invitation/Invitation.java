package com.projects.cofohelper.domain.invitation;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Invitation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long invitationId;
	
	@ManyToOne()
	Long groupId;
	
	@ManyToOne()
	Long inviterId;
	
	@ManyToOne()
	Long invitedId;
}
