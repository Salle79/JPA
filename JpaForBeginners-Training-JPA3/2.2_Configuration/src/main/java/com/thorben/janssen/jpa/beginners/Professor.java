package com.thorben.janssen.jpa.beginners;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Professor {

	@Id
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

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
}
