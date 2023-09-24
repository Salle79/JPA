package com.thorben.janssen.spring.data.repository;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import com.thorben.janssen.spring.data.model.ChessPlayer;

public class PlayerFragmentRepositoryImpl implements PlayerFragmentRepository {

    private Logger log = Logger.getLogger(PlayerFragmentRepositoryImpl.class.getSimpleName());

    private EntityManager em;

    public PlayerFragmentRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<ChessPlayer> getByTournamentName(String tournamentName) {
        log.info("Custom implementation of a fragment repository");

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ChessPlayer> cq = cb.createQuery(ChessPlayer.class);
        Root<ChessPlayer> player = cq.from(ChessPlayer.class);
        Join<Object, Object> tournament = player.join("tournaments", JoinType.INNER);
        cq.where(cb.equal(tournament.get("name"), tournamentName));

        return em.createQuery(cq).getResultList();
    }
}
