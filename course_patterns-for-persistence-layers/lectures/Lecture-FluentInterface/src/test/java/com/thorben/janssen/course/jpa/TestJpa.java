package com.thorben.janssen.course.jpa;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thorben.janssen.course.model.fluent.jpa.ChessGame;
import com.thorben.janssen.course.model.fluent.jpa.ChessMove;
import com.thorben.janssen.course.model.fluent.jpa.MoveColor;

public class TestJpa {

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

	@Test
	public void useFluentApi() {
		log.info("... useFluentApi ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		ChessGame game = new ChessGame().playedOnDate(LocalDate.now())
                                        .whitePiecesPlayedBy("Magnus Carlsen")
                                        .blackPiecesPlayedBy("Fabiano Caruana");
        em.persist(game);


        ChessMove move1White = new ChessMove().playedByColor(MoveColor.WHITE)
                                              .playedOnMove(1)
                                              .playedMove("e4")
                                              .playedInGame(game);
        em.persist(move1White);

        game.getMoves().add(move1White);

        ChessMove move1Black = new ChessMove().playedByColor(MoveColor.BLACK)
                                              .playedOnMove(1)
                                              .playedMove("e5")
                                              .playedInGame(game);
        em.persist(move1Black);

        game.getMoves().add(move1Black);

		em.getTransaction().commit();
		em.close();
	}

	@Test
	public void queryGame() {
		log.info("... queryGame ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		TypedQuery<ChessGame> q = em.createQuery("SELECT g FROM ChessGame_Jpa g WHERE g.playerWhite = :playerWhite", ChessGame.class);
		q.setParameter("playerWhite", "Anish Giri");
		List<ChessGame> games = q.getResultList();

        games.forEach(g -> log.info(g));

		em.getTransaction().commit();
		em.close();
	}

}
