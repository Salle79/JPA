package org.thoughts.on.java.jpa.beginners;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Professor {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "professor_generator")
	@SequenceGenerator(name = "professor_generator", sequenceName = "professor_seq")
	private Long id;
	
	private String firstName;
	
	private String lastName;

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

	@Override
	public String toString() {
		return "Professor [id=" + id + ", firstName=" + firstName
				+ ", lastName=" + lastName + "]";
	}
}
