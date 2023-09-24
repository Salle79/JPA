package com.thorben.janssen.hibernate.performance.model;

import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.Date;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Book.class)
public abstract class Book_ {

	public static volatile SetAttribute<Book, Review> review;
	public static volatile SingularAttribute<Book, Publisher> publisher;
	public static volatile SingularAttribute<Book, Long> id;
	public static volatile SingularAttribute<Book, String> title;
	public static volatile SingularAttribute<Book, Date> publishingDate;
	public static volatile SingularAttribute<Book, Integer> version;
	public static volatile SetAttribute<Book, Author> authors;

	public static final String REVIEW = "review";
	public static final String PUBLISHER = "publisher";
	public static final String ID = "id";
	public static final String TITLE = "title";
	public static final String PUBLISHING_DATE = "publishingDate";
	public static final String VERSION = "version";
	public static final String AUTHORS = "authors";

}

