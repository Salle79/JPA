package com.thorben.janssen.hibernate.performance.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BookPublisherValue {

    Logger log = LogManager.getLogger(this.getClass().getName());

	private String publisher;
	private String title;

    public BookPublisherValue() {
        log.info("Default constructor");
    }

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
        log.info("Set publisher to "+publisher);
		this.publisher = publisher;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
        log.info("Set title to "+title);
		this.title = title;
	}

    @Override
    public String toString() {
        return "BookPublisherValue [publisher=" + publisher + ", title=" + title + "]";
    }
}
