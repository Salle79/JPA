package com.thorben.janssen.hibernate.performance.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Version;

@Entity
public class Author {

	@Id
	private Long id;

	@Version
	private int version;

	private String firstName;

	private String lastName;

	@ManyToMany(mappedBy = "authors")
	private Set<Book> books = new HashSet<Book>();

    public void setId(Long id) {
        this.id = id;
    }
    
	public Long getId() {
		return this.id;
	}

	public int getVersion() {
		return this.version;
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

	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}
	
	public void addBook(Book book) {
		this.books.add(book);
		book.getAuthors().add(this);
	}
	
	public void removeBook(Book book) {
		this.books.remove(book);
		book.getAuthors().remove(this);
	}
    
	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (firstName != null && !firstName.trim().isEmpty())
			result += "firstName: " + firstName;
		if (lastName != null && !lastName.trim().isEmpty())
			result += ", lastName: " + lastName;
		return result;
	}
}