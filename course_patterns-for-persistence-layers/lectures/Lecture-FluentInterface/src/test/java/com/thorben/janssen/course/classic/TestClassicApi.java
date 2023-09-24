package com.thorben.janssen.course.classic;

import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thorben.janssen.course.model.classic.ChessGame;
import com.thorben.janssen.course.model.classic.ChessMove;
import com.thorben.janssen.course.model.classic.MoveColor;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class TestClassicApi {
	
    Logger log = LogManager.getLogger(this.getClass().getName());

	private EntityManagerFactory emf;

	@Before
	public void init() {
		emf = Persistence.createEntityManagerFactory("my-persistence-unit");
	}

	@After
	public void close() {
		emf.close();
	}
	
	@Test
	public void useClassicApi() {
		log.info("... useClassicApi ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		// create a new ChessGame with a few moves
		ChessGame game = new ChessGame();
        game.setDate(LocalDate.now());
        game.setPlayerWhite("Magnus Carlsen");
        game.setPlayerBlack("Fabiano Caruana");
        em.persist(game);

        ChessMove move1White = new ChessMove();
        move1White.setColor(MoveColor.WHITE);
        move1White.setMoveNumber(1);
        move1White.setMove("e4");
        move1White.setGame(game);
        game.getMoves().add(move1White);
        em.persist(move1White);

        ChessMove move1Black = new ChessMove();
        move1Black.setColor(MoveColor.BLACK);
        move1Black.setMoveNumber(1);
        move1Black.setMove("e5");
        move1Black.setGame(game);
        game.getMoves().add(move1Black);
        em.persist(move1Black);
        
		em.getTransaction().commit();
		em.close();
	}

}
