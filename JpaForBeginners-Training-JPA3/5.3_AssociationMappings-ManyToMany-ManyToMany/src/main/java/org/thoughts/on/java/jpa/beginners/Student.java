package org.thoughts.on.java.jpa.beginners;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_generator")
	@SequenceGenerator(name = "student_generator", sequenceName = "student_seq")
	private Long id;
	
	private String firstName;
	
	private String lastName;
	
	private StudentStatus state;
	
	@ManyToMany(mappedBy = "students")
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

	public StudentStatus getState() {
		return state;
	}

	public void setState(StudentStatus state) {
		this.state = state;
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
	
	public void enrollToCourse(Course course) {
		this.courses.add(course);
		course.getStudents().add(this);
	}
	
	public void unenrollFromCourse(Course course) {
		this.courses.remove(course);
		course.getStudents().remove(this);
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", firstName=" + firstName + ", lastName="
				+ lastName + ", state=" + state + "]";
	}
}
