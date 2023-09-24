package org.thoughts.on.java.jpa.beginners;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Version;

@Entity
public class Curriculum {

	@OneToOne
	@JoinColumn(name = "id")
	@MapsId
	private Course course;
	
	
	
	@Id
	private Long id;
	
	private String description;
	
	
	
	@Version
	private int version;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Long getId() {
		return id;
	}

	public int getVersion() {
		return version;
	}
}
