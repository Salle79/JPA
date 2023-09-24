package com.thorben.janssen.course;

import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thorben.janssen.course.model.ChessGame;
import com.thorben.janssen.course.model.ChessMove;
import com.thorben.janssen.course.model.MoveColor;
import com.thorben.janssen.course.model.builder.ChessGameBuilder;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class TestBuilder {

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
	public void useBuilderApi() {
		log.info("... useBuilderApi ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

        ChessGame game = new ChessGameBuilder()
                                      .withPlayerWhite("Magnus Carlsen")
                                      .withPlayerBlack("Fabiano Caruana")
                                      .withDate(LocalDate.now())
                                      .withRound(1)
                                      .withMoves()
                                        .addMove()
                                            .withMoveNumber(1)
                                            .withColor(MoveColor.WHITE)
                                            .withMove("e4")
                                            .addToList()
                                        .addMove()
                                            .withMoveNumber(1)
                                            .withColor(MoveColor.BLACK)
                                            .withMove("e5")
                                            .addToList()
                                        .endMoves()
                                      .buildChessGame();
        em.persist(game);

		em.getTransaction().commit();
		em.close();
	}

    @Test
	public void useImprovedBuilderApi() {
		log.info("... useImprovedBuilderApi ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

        ChessGame game = new ChessGameBuilder()
                                      .withPlayerWhite("Magnus Carlsen")
                                      .withPlayerBlack("Fabiano Caruana")
                                      .withDate(LocalDate.now())
                                      .withRound(1)
                                      .withMoves()
                                        .addNextMove("e4")
                                        .addNextMove("e5")
                                        .endMoves()
                                      .buildChessGame();
        em.persist(game);

		em.getTransaction().commit();
		em.close();
	}

	@Test
	public void useClassicApi() {
		log.info("... useClassicApi ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		ChessGame game = new ChessGame();
        game.setDate(LocalDate.now());
        game.setRound(1);
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
