package com.thorben.janssen.hibernate.performance.model;

import java.time.LocalDate;

public class BookValue {

	private Long id;

	private int version;

	private String title;

	private LocalDate publishingDate;

    private Long publisherId;

	public BookValue(Long id, int version, String title, LocalDate publishingDate, Long publisherId) {
		this.id = id;
		this.version = version;
		this.title = title;
		this.publishingDate = publishingDate;
        this.publisherId = publisherId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getPublishingDate() {
		return publishingDate;
	}

	public void setPublishingDate(LocalDate publishingDate) {
		this.publishingDate = publishingDate;
	}

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }
}
