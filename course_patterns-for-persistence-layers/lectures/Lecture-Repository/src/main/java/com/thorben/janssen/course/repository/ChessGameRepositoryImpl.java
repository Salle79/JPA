package com.thorben.janssen.course.repository;

import java.util.List;

import com.thorben.janssen.course.model.ChessGame;

import jakarta.persistence.EntityManager;

public class ChessGameRepositoryImpl implements ChessGameRepository {

	private EntityManager em;
	
	public ChessGameRepositoryImpl(EntityManager em) {
		this.em = em;
	}
	
	@Override
	public ChessGame getChessGameById(Long id) {
		return em.find(ChessGame.class, id);
	}

    @Override
    public List<ChessGame> getGamesByPlayers(String playerWhite, String playerBlack) {
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
        game.getMoves().forEach(move -> em.persist(move));
    }

    @Override
    public void deleteChessGame(ChessGame game) {
        game.getMoves().forEach(move -> em.remove(move));
        em.remove(game);
    }
}