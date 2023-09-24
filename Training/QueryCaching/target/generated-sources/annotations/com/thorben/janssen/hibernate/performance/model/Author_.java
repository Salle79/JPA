package com.thorben.janssen.hibernate.performance.model;

import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Author.class)
public abstract class Author_ {

	public static volatile SingularAttribute<Author, String> firstName;
	public static volatile SingularAttribute<Author, String> lastName;
	public static volatile SetAttribute<Author, Book> books;
	public static volatile SingularAttribute<Author, Long> id;
	public static volatile SingularAttribute<Author, Integer> version;

	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String BOOKS = "books";
	public static final String ID = "id";
	public static final String VERSION = "version";

}

