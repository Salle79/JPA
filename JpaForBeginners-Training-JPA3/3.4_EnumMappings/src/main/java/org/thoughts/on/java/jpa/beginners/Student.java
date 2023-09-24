package org.thoughts.on.java.jpa.beginners;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Version;

@Entity
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_generator")
	@SequenceGenerator(name = "student_generator", sequenceName = "student_seq")
	private Long id;
	
	private String firstName;
	
	private String lastName;
	
//	@Enumerated(EnumType.ORDINAL)
	@Enumerated(EnumType.STRING)
	private StudentStatus state;
	
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

	public StudentStatus getState() {
		return state;
	}

	public void setState(StudentStatus state) {
		this.state = state;
	}

	public Long getId() {
		return id;
	}

	public long getVersion() {
		return version;
	}
}
