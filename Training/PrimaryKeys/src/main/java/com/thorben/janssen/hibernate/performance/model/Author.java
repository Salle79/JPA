package com.thorben.janssen.hibernate.performance.model;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Version;

@Entity
public class Author {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    // Hibernate <6: hibernate_seq without optimization - Hibernate >=6: Author_SEQ with optimization
    // @GeneratedValue(strategy = GenerationType.SEQUENCE)

    // Sequence name defined by @SequenceGenerator with optimization
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_generator")
    // @SequenceGenerator(name = "author_generator", sequenceName = "author_seqence", allocationSize = 10)

    // Hibernate >=5.3: Generator name is used as sequence name and optimization is activated
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_sequence")

    // Hibernate <5: Optimizer needs to get activated
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_generator")
    // @GenericGenerator(name = "author_generator", 
    // 					strategy = "enhanced-sequence", 
    // 					parameters = {	@Parameter(name = "sequence_name", value = "authorseq"),
    // 									@Parameter(name = "increment_size", value = "10"),
    // 									@Parameter(name = "optimizer", value = "pooled-lo") }) 

    // Don't use this - Legacy
    // @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;                    
	
	@Version
	@Column(name = "version")
	private int version;

	@Column
	private String firstName;

	@Column
	private String lastName;

	@ManyToMany(mappedBy = "authors")
	private Set<Book> books = new HashSet<Book>();

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Author)) {
			return false;
		}
		Author other = (Author) obj;
		if (id != null) {
			if (!id.equals(other.id)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
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

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (firstName != null && !firstName.trim().isEmpty())
			result += "firstName: " + firstName;
		if (lastName != null && !lastName.trim().isEmpty())
			result += ", lastName: " + lastName;
		return result;
	}

	public Set<Book> getBooks() {
		return this.books;
	}

	public void setBooks(final Set<Book> books) {
		this.books = books;
	}
}