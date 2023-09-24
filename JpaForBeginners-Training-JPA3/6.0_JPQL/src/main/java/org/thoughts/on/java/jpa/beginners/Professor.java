package org.thoughts.on.java.jpa.beginners;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Version;

@Entity
public class Professor {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "professor_generator")
	@SequenceGenerator(name = "professor_generator", sequenceName = "professor_seq")
	private Long id;

	private String firstName;

	private String lastName;

	@OneToMany(mappedBy = "professor")
	private List<Course> courses = new ArrayList<Course>();

	@Version
	private long version;

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

	public long getVersion() {
		return version;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	@Override
	public String toString() {
		return "Professor [id=" + id + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", version=" + version + "]";
	}

}
