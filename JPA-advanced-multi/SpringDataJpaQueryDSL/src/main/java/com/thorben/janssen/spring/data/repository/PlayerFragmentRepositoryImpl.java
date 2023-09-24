package com.thorben.janssen.spring.data.repository;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;

import com.querydsl.jpa.impl.JPAQuery;
import com.thorben.janssen.spring.data.model.ChessPlayer;
import com.thorben.janssen.spring.data.model.QChessPlayer;
import com.thorben.janssen.spring.data.model.QChessTournament;

public class PlayerFragmentRepositoryImpl implements PlayerFragmentRepository {

    private Logger log = Logger.getLogger(PlayerFragmentRepositoryImpl.class.getSimpleName());

    private EntityManager em;

    public PlayerFragmentRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<ChessPlayer> getByTournamentName(String tournamentName) {
        log.info("Custom implementation of a fragment repository");

        QChessPlayer qChessPlayer = QChessPlayer.chessPlayer;
        QChessTournament qChessTournament = QChessTournament.chessTournament;
        JPAQuery<ChessPlayer> query = new JPAQuery(this.em);

        return query.select(qChessPlayer)
                    .from(qChessPlayer)
                    .join(qChessPlayer.tournaments, qChessTournament)
                    .where(qChessTournament.name.eq(tournamentName))
                    .fetch();
    }
}
