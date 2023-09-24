package com.thorben.janssen.spring.data.repository;

import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.thorben.janssen.spring.data.model.ChessTournament;

public class FindTournamentByNameImpl implements FindTournamentByName {

    private Logger log = Logger.getLogger(FindTournamentByNameImpl.class.getSimpleName());

    private EntityManager em;

    public FindTournamentByNameImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public ChessTournament findByName(String name) {
        log.info("FindTournamentByNameImpl.findByName");
        TypedQuery<ChessTournament> q = em.createQuery("SELECT t FROM ChessTournament t "
                                                       + "WHERE t.name = :name", ChessTournament.class);
        q.setParameter("name", name);
        return q.getSingleResult();
    }
    
}
