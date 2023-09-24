package org.thoughts.on.java.university.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class Professor {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "professor_seq")
	@SequenceGenerator(name = "professor_seq", sequenceName = "professor_seq")
	private Long id;
	
	private String firstName;
	
	private String lastName;
	
	@OneToMany(mappedBy = "professor")
	private Set<Course> courses = new HashSet<Course>();

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Long getId() {
		return id;
	}

	public Set<Course> getCourses() {
		return courses;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}
}
