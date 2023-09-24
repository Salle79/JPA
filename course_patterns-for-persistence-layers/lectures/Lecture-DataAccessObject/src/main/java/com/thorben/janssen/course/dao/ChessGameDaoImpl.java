package com.thorben.janssen.course.dao;

import java.util.List;

import com.thorben.janssen.course.model.ChessGame;

import jakarta.persistence.EntityManager;

public class ChessGameDaoImpl implements ChessGameDao {

	private EntityManager em;
	
	public ChessGameDaoImpl(EntityManager em) {
		this.em = em;
	}
	
	@Override
	public ChessGame getChessGameById(Long id) {
		return em.find(ChessGame.class, id);
	}

    @Override
    public List<ChessGame> getChessGamesByPlayers(String playerWhite, String playerBlack) {
        return em.createQuery("""
                                SELECT g 
                                FROM ChessGame g 
                                WHERE g.playerWhite = :playerWhite 
                                    AND g.playerBlack = :playerBlack""", 
                              ChessGame.class)
                 .setParameter("playerWhite", playerWhite)
                 .setParameter("playerBlack", playerBlack)
                 .getResultList();
    }

    @Override
    public void persistChessGame(ChessGame game) {
        em.persist(game);
    }

    @Override
    public void deleteChessGame(ChessGame game) {
        em.remove(game);
    }
}