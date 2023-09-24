package com.thorben.janssen.hibernate.performance.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Version;

@Entity
public class Book {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_generator")
    @SequenceGenerator(name = "book_generator", initialValue = 100)
	private Long id;

	@Version
	private int version;

	private String title;
	
	private Format format;

	private LocalDate publishingDate;
	
	@ManyToMany
    @JoinTable(name = "book_author", 
            joinColumns = { @JoinColumn(name = "book_id") }, 
            inverseJoinColumns = { @JoinColumn(name = "author_id") })
    // private List<Author> authors = new ArrayList<Author>();
    private Set<Author> authors = new HashSet<Author>();

    @OneToOne(mappedBy = "book", fetch = FetchType.LAZY)
    private Manuscript manuscript;

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return this.id;
	}

	public int getVersion() {
		return this.version;
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

	public Set<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<Author> authors) {
		this.authors = authors;
	}

    // public List<Author> getAuthors() {
	// 	return authors;
	// }

	// public void setAuthors(List<Author> authors) {
	// 	this.authors = authors;
	// }
	
	public Format getFormat() {
		return format;
	}

	public void setFormat(Format format) {
		this.format = format;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (title != null && !title.trim().isEmpty())
			result += "title: " + title;
		return result;
	}
}