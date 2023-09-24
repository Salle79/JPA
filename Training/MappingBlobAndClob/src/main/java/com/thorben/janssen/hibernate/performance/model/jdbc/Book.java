package com.thorben.janssen.hibernate.performance.model.jdbc;

import java.sql.Blob;
import java.sql.Clob;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity(name = "BookLocator")
public class Book {

	@Id
	@GeneratedValue
	private Long id;

	private String title;

	@Lob
	private Clob content;

	@Lob
	private Blob cover;

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

	public Clob getContent() {
		return content;
	}

	public void setContent(Clob content) {
		this.content = content;
	}

	public Blob getCover() {
		return cover;
	}

	public void setCover(Blob cover) {
		this.cover = cover;
	}

}
