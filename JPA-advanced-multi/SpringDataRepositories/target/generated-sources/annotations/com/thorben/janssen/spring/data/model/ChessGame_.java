package com.thorben.janssen.spring.data.model;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDate;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ChessGame.class)
public abstract class ChessGame_ {

	public static volatile SingularAttribute<ChessGame, LocalDate> date;
	public static volatile SingularAttribute<ChessGame, Integer> round;
	public static volatile SingularAttribute<ChessGame, ChessPlayer> playerWhite;
	public static volatile SingularAttribute<ChessGame, ChessPlayer> playerBlack;
	public static volatile SingularAttribute<ChessGame, ChessTournament> chessTournament;
	public static volatile SingularAttribute<ChessGame, Long> id;
	public static volatile SingularAttribute<ChessGame, Integer> version;

	public static final String DATE = "date";
	public static final String ROUND = "round";
	public static final String PLAYER_WHITE = "playerWhite";
	public static final String PLAYER_BLACK = "playerBlack";
	public static final String CHESS_TOURNAMENT = "chessTournament";
	public static final String ID = "id";
	public static final String VERSION = "version";

}

