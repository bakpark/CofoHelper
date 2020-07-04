package com.projects.cofohelper.domain.problem;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class HtmlNode {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	private String tag;
	
	@Column(length = ((1<<16)-1))
	private String innerText;
	
	public HtmlNode(String tag, HtmlNode parent) {
		this.tag = tag;
		this.parent = parent;
		this.innerText = "";
		attributes = new ArrayList<>();
		childs = new ArrayList<>();
	}

	
	@OneToMany(mappedBy = "htmlNode")
	private List<HtmlAttribute> attributes;

	
	@OneToMany(mappedBy = "parent")
	private List<HtmlNode> childs;

	@JsonBackReference
	@ManyToOne(targetEntity = HtmlNode.class)
	private HtmlNode parent;

	public void insertAttributes(HtmlAttribute attribute) {
		if(attributes == null) attributes = new ArrayList<HtmlAttribute>();
		attributes.add(attribute);
	}
	
	public void insertChild(HtmlNode child) {
		if(childs == null) childs = new ArrayList<HtmlNode>();
		childs.add(child);
	}
}
