package com.projects.cofohelper.domain.group;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.projects.cofohelper.domain.contest.Contest;
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
@Table(name = "GROUP_")
public class Group {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long groupId;
	
	private String groupName;
	
	public Group(String name) {
		groupName = name;
	}
	@JsonBackReference
	@OneToMany(mappedBy = "group")
	private List<PartyInfo> parties;
	
	@JsonBackReference
	@OneToMany(mappedBy = "group")
	private List<Invitation> invitations;
	
	@JsonBackReference
	@OneToMany(mappedBy = "group")
	private List<Contest> contests;

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
	
}
