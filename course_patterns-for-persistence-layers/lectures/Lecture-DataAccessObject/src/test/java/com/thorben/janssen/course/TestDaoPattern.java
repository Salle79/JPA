package com.thorben.janssen.course;

import java.time.LocalDate;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thorben.janssen.course.dao.ChessGameDao;
import com.thorben.janssen.course.dao.ChessGameDaoImpl;
import com.thorben.janssen.course.dao.ChessMoveDao;
import com.thorben.janssen.course.dao.ChessMoveDaoImpl;
import com.thorben.janssen.course.model.ChessGame;
import com.thorben.janssen.course.model.ChessMove;
import com.thorben.janssen.course.model.MoveColor;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import static org.assertj.core.api.Assertions.assertThat;


public class TestDaoPattern {

    Logger log = LogManager.getLogger(this.getClass().getName());

	private EntityManagerFactory emf;


	@Test
	public void testDaoPersist() {
		log.info("... testDaoPersist ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
        // create and persist a new ChessGame
		ChessGame game = new ChessGame();
        game.setDate(LocalDate.now());
        game.setPlayerWhite("Magnus Carlsen");
        game.setPlayerBlack("Fabiano Caruana");

        ChessGameDao gameDao = getChessGameDao(em);
        gameDao.persistChessGame(game);


        // create and persist a new ChessMove
        ChessMove move1White = new ChessMove();
        move1White.setColor(MoveColor.WHITE);
        move1White.setMoveNumber(1);
        move1White.setMove("e4");
        move1White.setGame(game);
        game.getMoves().add(move1White);

        ChessMoveDao moveDao = getChessMoveDao(em);
        moveDao.persistChessMove(move1White);

        
        // create and persist a new ChessMove
        ChessMove move1Black = new ChessMove();
        move1Black.setColor(MoveColor.BLACK);
        move1Black.setMoveNumber(1);
        move1Black.setMove("e5");
        move1Black.setGame(game);
        game.getMoves().add(move1Black);

        moveDao.persistChessMove(move1Black);
		
		em.getTransaction().commit();
		em.close();
	}

    @Test
    public void testGetGamesByPlayers() {
        log.info("testGetGamesByPlayers");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

        ChessGameDao gameDao = getChessGameDao(em);
        List<ChessGame> games = gameDao.getChessGamesByPlayers("Anish Giri", "Magnus Carlsen");
        
        assertThat(games).size().isEqualTo(1);
        games.forEach(game -> log.info(game.getPlayerWhite() + " played against " + game.getPlayerBlack() + " a game with " + game.getMoves().size() + " moves."));        		
        
		em.getTransaction().commit();
		em.close();
    }

	
    private ChessGameDao getChessGameDao(EntityManager em) {
        return new ChessGameDaoImpl(em);
    }

    private ChessMoveDao getChessMoveDao(EntityManager em) {
        return new ChessMoveDaoImpl(em);
    }

	@Before
	public void init() {
		emf = Persistence.createEntityManagerFactory("my-persistence-unit");
	}

	@After
	public void close() {
		emf.close();
	}
}
