package org.thoughts.on.java.university.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

@Entity
@NamedQuery(name = Student.QUERY_STUDENT_BY_FIRSTNAME_AND_LASTNAME, 
			query = "SELECT s FROM Student s WHERE s.firstName = :"+Student.PARAM_FIRSTNAME+" AND s.lastName = :"+Student.PARAM_LASTNAME)
public class Student {

	public static final String QUERY_STUDENT_BY_FIRSTNAME_AND_LASTNAME = "query.StudentByFirstNameAndLastName";
	
	public static final String PARAM_FIRSTNAME = "firstName";
	public static final String PARAM_LASTNAME = "lastName";
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_seq")
	@SequenceGenerator(name = "student_seq", sequenceName = "student_seq")
	private Long id;
	
	private String firstName;
	
	private String lastName;
	
	@Enumerated(EnumType.ORDINAL)
	private StudentState state;
	
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

	public StudentState getState() {
		return state;
	}

	public void setState(StudentState state) {
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
	
	
}
