package com.thorben.janssen.spring.data.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChessGame is a Querydsl query type for ChessGame
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChessGame extends EntityPathBase<ChessGame> {

    private static final long serialVersionUID = 534902293L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChessGame chessGame = new QChessGame("chessGame");

    public final QChessTournament chessTournament;

    public final DatePath<java.time.LocalDate> date = createDate("date", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QChessPlayer playerBlack;

    public final QChessPlayer playerWhite;

    public final NumberPath<Integer> round = createNumber("round", Integer.class);

    public final NumberPath<Integer> version = createNumber("version", Integer.class);

    public QChessGame(String variable) {
        this(ChessGame.class, forVariable(variable), INITS);
    }

    public QChessGame(Path<? extends ChessGame> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChessGame(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChessGame(PathMetadata metadata, PathInits inits) {
        this(ChessGame.class, metadata, inits);
    }

    public QChessGame(Class<? extends ChessGame> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.chessTournament = inits.isInitialized("chessTournament") ? new QChessTournament(forProperty("chessTournament")) : null;
        this.playerBlack = inits.isInitialized("playerBlack") ? new QChessPlayer(forProperty("playerBlack")) : null;
        this.playerWhite = inits.isInitialized("playerWhite") ? new QChessPlayer(forProperty("playerWhite")) : null;
    }

}

