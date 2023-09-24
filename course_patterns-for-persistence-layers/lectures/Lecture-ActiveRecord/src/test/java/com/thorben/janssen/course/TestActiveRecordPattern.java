package com.thorben.janssen.course;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thorben.janssen.course.model.ChessGame;
import com.thorben.janssen.course.util.HibernateSessionHandling;


public class TestActiveRecordPattern {

    Logger log = LogManager.getLogger(this.getClass().getName());

	@Test
	public void testActiveRecordPersist() {
		log.info("... testActiveRecordPersist ...");

        HibernateSessionHandling.openTransaction();

        // create and persist a new ChessGame
		ChessGame game = new ChessGame();
        game.setDate(LocalDate.now());
        game.setPlayerWhite("Magnus Carlsen");
        game.setPlayerBlack("Fabiano Caruana");
        
        game.persist();

        HibernateSessionHandling.commitSession(); 
	}

    @Test
    public void testGetGamesByPlayers() {
        log.info("testGetGamesByPlayers");

		HibernateSessionHandling.openTransaction();

        List<ChessGame> games = ChessGame.getGamesByPlayers("Anish Giri", "Magnus Carlsen");
        
        assertThat(games).size().isEqualTo(1);
		games.forEach(game -> log.info(game.getPlayerWhite() + " played against " + game.getPlayerBlack() + " a game with " + game.getMoves().size() + " moves."));
		        		
        HibernateSessionHandling.commitSession(); 
    }



	@Before
	public void init() {
		HibernateSessionHandling.start();
	}

	@After
	public void close() {
		HibernateSessionHandling.shutdown();
	}
}
