package com.projects.cofohelper.domain.group;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

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
	@OneToMany(mappedBy = "group", cascade = CascadeType.REMOVE)
	private List<PartyInfo> parties;

	@JsonBackReference
	@OneToMany(mappedBy = "group", cascade = CascadeType.REMOVE)
	private List<Invitation> invitations;

	@JsonBackReference
	@OneToMany(mappedBy = "group", cascade = CascadeType.REMOVE)
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

	public void addContest(Contest contest) {
		if(contests == null)
			contests = new ArrayList<Contest>();
		contests.add(contest);
	}


}
