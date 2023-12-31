package org.thoughts.on.java.jpa.beginners;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.TableGenerator;

@Entity
public class Professor {

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "professor_generator")
//	@SequenceGenerator(name = "professor_generator", sequenceName = "professor_seq")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "professor_generator")
	@TableGenerator(name = "professor_generator", table = "sequence_table")
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
}
