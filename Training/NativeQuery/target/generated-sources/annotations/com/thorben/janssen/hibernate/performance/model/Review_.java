package com.thorben.janssen.hibernate.performance.model;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Review.class)
public abstract class Review_ {

	public static volatile SingularAttribute<Review, Book> book;
	public static volatile SingularAttribute<Review, Rating> rating;
	public static volatile SingularAttribute<Review, String> comment;
	public static volatile SingularAttribute<Review, Long> id;
	public static volatile SingularAttribute<Review, Integer> version;

	public static final String BOOK = "book";
	public static final String RATING = "rating";
	public static final String COMMENT = "comment";
	public static final String ID = "id";
	public static final String VERSION = "version";

}

