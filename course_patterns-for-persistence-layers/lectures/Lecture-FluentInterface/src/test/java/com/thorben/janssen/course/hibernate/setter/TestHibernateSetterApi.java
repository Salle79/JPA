package com.thorben.janssen.course.hibernate.setter;

import java.time.LocalDate;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thorben.janssen.course.model.fluent.hibernate.setter.ChessGame;
import com.thorben.janssen.course.model.fluent.hibernate.setter.ChessMove;
import com.thorben.janssen.course.model.fluent.hibernate.setter.MoveColor;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class TestHibernateSetterApi {

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
	public void persistGame() {
		log.info("... persistGame ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		ChessGame game = new ChessGame().setDate(LocalDate.now())
                                        .setPlayerWhite("Magnus Carlsen")
                                        .setPlayerBlack("Fabiano Caruana");
        em.persist(game);


        ChessMove move1White = new ChessMove().setColor(MoveColor.WHITE)
                                              .setMoveNumber(1)
                                              .setMove("e4")
                                              .setGame(game);
        em.persist(move1White);

        game.getMoves().add(move1White);

        ChessMove move1Black = new ChessMove().setColor(MoveColor.BLACK)
                                              .setMoveNumber(1)
                                              .setMove("e5")
                                              .setGame(game);
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

		TypedQuery<ChessGame> q = em.createQuery("SELECT g FROM ChessGame_HibernateSetter g WHERE g.playerWhite = :playerWhite", ChessGame.class);
		q.setParameter("playerWhite", "Anish Giri");
		List<ChessGame> games = q.getResultList();

        games.forEach(g -> log.info(g));

		em.getTransaction().commit();
		em.close();
	}

}
