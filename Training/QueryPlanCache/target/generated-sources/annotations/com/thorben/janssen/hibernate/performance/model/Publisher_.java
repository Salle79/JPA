package com.thorben.janssen.hibernate.performance.model;

import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Publisher.class)
public abstract class Publisher_ {

	public static volatile SetAttribute<Publisher, Book> books;
	public static volatile SingularAttribute<Publisher, String> name;
	public static volatile SingularAttribute<Publisher, Long> id;
	public static volatile SingularAttribute<Publisher, Integer> version;

	public static final String BOOKS = "books";
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String VERSION = "version";

}

