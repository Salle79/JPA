package com.thorben.janssen.course.dao;

import java.util.List;

import com.thorben.janssen.course.model.ChessMove;

import jakarta.persistence.EntityManager;

public class ChessMoveDaoImpl implements ChessMoveDao {

    private EntityManager em;
	
	public ChessMoveDaoImpl(EntityManager em) {
		this.em = em;
	}

    @Override
    public List<ChessMove> getChessMovesForGame(Long gameId) {
        return em.createQuery("""
                                SELECT m 
                                FROM ChessMove m JOIN m.game g
                                WHERE g.id = :gameId""", 
                              ChessMove.class)
                 .setParameter("gameId", gameId)
                 .getResultList();
    }

    @Override
    public void persistChessMove(ChessMove move) {
        em.persist(move);
    }

    @Override
    public void deleteChessMove(ChessMove move) {
        em.remove(move);
    }
    
}
