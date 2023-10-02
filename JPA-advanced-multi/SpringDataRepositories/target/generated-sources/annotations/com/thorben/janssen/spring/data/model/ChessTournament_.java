package com.thorben.janssen.spring.data.model;

import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDate;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ChessTournament.class)
public abstract class ChessTournament_ {

	public static volatile SingularAttribute<ChessTournament, LocalDate> endDate;
	public static volatile SetAttribute<ChessTournament, ChessPlayer> players;
	public static volatile SingularAttribute<ChessTournament, String> name;
	public static volatile SetAttribute<ChessTournament, ChessGame> games;
	public static volatile SingularAttribute<ChessTournament, Long> id;
	public static volatile SingularAttribute<ChessTournament, Integer> version;
	public static volatile SingularAttribute<ChessTournament, LocalDate> startDate;

	public static final String END_DATE = "endDate";
	public static final String PLAYERS = "players";
	public static final String NAME = "name";
	public static final String GAMES = "games";
	public static final String ID = "id";
	public static final String VERSION = "version";
	public static final String START_DATE = "startDate";

}

