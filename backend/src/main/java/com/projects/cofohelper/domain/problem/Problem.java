package com.projects.cofohelper.domain.problem;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Problem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long problemId;

	// example : 1367-A
	private String name;
	
	@JsonBackReference
	@OneToOne(targetEntity = HtmlNode.class)
	private HtmlNode htmlContent;
	
	public Problem(String name) {
		this.name = name;
	}
	
	
}
