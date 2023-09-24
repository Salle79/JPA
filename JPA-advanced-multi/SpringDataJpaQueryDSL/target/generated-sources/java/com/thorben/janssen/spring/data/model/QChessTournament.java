package com.thorben.janssen.spring.data.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChessTournament is a Querydsl query type for ChessTournament
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QChessTournament extends EntityPathBase<ChessTournament> {

    private static final long serialVersionUID = -1167680052L;

    public static final QChessTournament chessTournament = new QChessTournament("chessTournament");

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final SetPath<ChessGame, QChessGame> games = this.<ChessGame, QChessGame>createSet("games", ChessGame.class, QChessGame.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final SetPath<ChessPlayer, QChessPlayer> players = this.<ChessPlayer, QChessPlayer>createSet("players", ChessPlayer.class, QChessPlayer.class, PathInits.DIRECT2);

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public final NumberPath<Integer> version = createNumber("version", Integer.class);

    public QChessTournament(String variable) {
        super(ChessTournament.class, forVariable(variable));
    }

    public QChessTournament(Path<? extends ChessTournament> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChessTournament(PathMetadata metadata) {
        super(ChessTournament.class, metadata);
    }

}

