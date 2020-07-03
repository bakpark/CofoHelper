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
	public Long groupId;
	
	public String groupName;
	
	public Group(String name) {
		groupName = name;
	}
	@JsonBackReference
	@OneToMany(mappedBy = "group")
	@Builder.Default
	public List<PartyInfo> parties = new ArrayList<PartyInfo>();
	

	public void addPartyInfo(PartyInfo info) {
		parties.add(info);
	}
	
}
