package com.thorben.janssen.spring.data.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChessPlayer is a Querydsl query type for ChessPlayer
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QChessPlayer extends EntityPathBase<ChessPlayer> {

    private static final long serialVersionUID = -1087485884L;

    public static final QChessPlayer chessPlayer = new QChessPlayer("chessPlayer");

    public final DatePath<java.time.LocalDate> birthDate = createDate("birthDate", java.time.LocalDate.class);

    public final StringPath firstName = createString("firstName");

    public final SetPath<ChessGame, QChessGame> gamesBlack = this.<ChessGame, QChessGame>createSet("gamesBlack", ChessGame.class, QChessGame.class, PathInits.DIRECT2);

    public final SetPath<ChessGame, QChessGame> gamesWhite = this.<ChessGame, QChessGame>createSet("gamesWhite", ChessGame.class, QChessGame.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastName = createString("lastName");

    public final SetPath<ChessTournament, QChessTournament> tournaments = this.<ChessTournament, QChessTournament>createSet("tournaments", ChessTournament.class, QChessTournament.class, PathInits.DIRECT2);

    public final NumberPath<Integer> version = createNumber("version", Integer.class);

    public QChessPlayer(String variable) {
        super(ChessPlayer.class, forVariable(variable));
    }

    public QChessPlayer(Path<? extends ChessPlayer> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChessPlayer(PathMetadata metadata) {
        super(ChessPlayer.class, metadata);
    }

}

