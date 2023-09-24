package com.thorben.janssen.hibernate.performance.model.materialized.lazy;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity(name="BookLazy")
public class Book {

	@Id
	@GeneratedValue
	private Long id;

	private String title;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
