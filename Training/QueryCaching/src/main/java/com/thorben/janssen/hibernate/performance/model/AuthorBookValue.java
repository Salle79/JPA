package com.thorben.janssen.hibernate.performance.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuthorBookValue {

	private Logger log = LogManager.getLogger(AuthorBookValue.class.getSimpleName());
	
	private String firstName;
	
	private String lastName;
	
	private String title;

	public AuthorBookValue(String firstName, String lastName, String title) {
		log.info("Constructor with parameters of AuthorBookValue");
		this.firstName = firstName;
		this.lastName = lastName;
		this.title = title;
	}
	
	public AuthorBookValue() {
		log.info("Default constructor of AuthorBookValue");
	}

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
