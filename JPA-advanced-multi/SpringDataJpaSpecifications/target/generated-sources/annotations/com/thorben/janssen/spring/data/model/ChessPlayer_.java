package com.thorben.janssen.spring.data.model;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ChessPlayer.class)
public abstract class ChessPlayer_ {

	public static volatile SetAttribute<ChessPlayer, ChessGame> gamesWhite;
	public static volatile SingularAttribute<ChessPlayer, String> firstName;
	public static volatile SingularAttribute<ChessPlayer, String> lastName;
	public static volatile SetAttribute<ChessPlayer, ChessTournament> tournaments;
	public static volatile SingularAttribute<ChessPlayer, Long> id;
	public static volatile SingularAttribute<ChessPlayer, LocalDate> birthDate;
	public static volatile SingularAttribute<ChessPlayer, Integer> version;
	public static volatile SetAttribute<ChessPlayer, ChessGame> gamesBlack;

	public static final String GAMES_WHITE = "gamesWhite";
	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String TOURNAMENTS = "tournaments";
	public static final String ID = "id";
	public static final String BIRTH_DATE = "birthDate";
	public static final String VERSION = "version";
	public static final String GAMES_BLACK = "gamesBlack";

}

