package com.thorben.janssen.spring.data.repository;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.thorben.janssen.spring.data.model.ChessTournament;

public class FindTournamentsImpl implements FindTournaments {
    
    private Logger log = Logger.getLogger(FindTournamentsImpl.class.getSimpleName());

    private EntityManager em;

    public FindTournamentsImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public ChessTournament findByName(String name) {
        log.info("FindTournamentsImpl.findByName");
        TypedQuery<ChessTournament> q = em.createQuery("SELECT t FROM ChessTournament t WHERE t.name = :name", 
                                                        ChessTournament.class);
        q.setParameter("name", name);
        return q.getSingleResult();
    }

    @Override
    public List<ChessTournament> findByPlayerId(Long playerId) {
        log.info("FindTournamentsImpl.findByPlayerId");
        TypedQuery<ChessTournament> q = em.createQuery("SELECT t FROM ChessTournament t JOIN t.players p WHERE p.id = :pid", 
                                                        ChessTournament.class);
        q.setParameter("pid", playerId);
        return q.getResultList();
    }
}
