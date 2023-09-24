package com.thorben.janssen.spring.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.thorben.janssen.spring.data.model.ChessPlayer;
import com.thorben.janssen.spring.data.repository.ChessPlayerRepository;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Commit
public class TestDerivedQueries {

    private static final Logger log = LoggerFactory.getLogger(TestDerivedQueries.class);

    @Autowired
    private ChessPlayerRepository playerRepo;

    @Test
    public void testFindByLastName() {
        log.info("... testFindByLastName ...");

        List<ChessPlayer> players = playerRepo.findByLastName("Carlsen");
        assertThat(players.size()).isEqualTo(1);
        
        ChessPlayer player = players.get(0);
        log.info(player.getFirstName() + " " + player.getLastName()); 
    }

    @Test
    public void findByFirstNameAndLastName() {
        log.info("... findByFirstNameAndLastName ...");

        List<ChessPlayer> players = playerRepo.findByFirstNameAndLastName("Magnus", "Carlsen");
        assertThat(players.size()).isEqualTo(1);
        
        ChessPlayer player = players.get(0);
        log.info(player.getFirstName() + " " + player.getLastName()); 
    }

    @Test
    public void existsByLastName() {
        log.info("... existsByLastName ...");

        boolean exists = playerRepo.existsByLastName("Carlsen");
        assertThat(exists).isTrue();
        
        log.info("There is at least one player with the last name Carlsen."); 
    }

    @Test
    public void countByLastName() {
        log.info("... countByLastName ...");

        Long numPlayers = playerRepo.countByLastName("Doe");
        assertThat(numPlayers).isEqualTo(15);
        
        log.info("There are "+numPlayers+" with the last name Doe."); 
    }

    @Test
    public void findFirst10ByLastName() {
        log.info("... findFirst10ByLastName ...");

        List<ChessPlayer> players = playerRepo.findFirst10ByLastName("Doe");
        assertThat(players.size()).isEqualTo(10);
        
        log.info("The query returned "+players.size()+" players with the last name "+players.get(0).getLastName()); 
    }

    @Test
    public void findByLastNameLike() {
        log.info("... findByLastNameLike ...");

        List<ChessPlayer> players = playerRepo.findByLastNameLike("Carl%");
        assertThat(players.size()).isEqualTo(1);
        
        ChessPlayer player = players.get(0);
        log.info(player.getFirstName() + " " + player.getLastName()); 
    }

    @Test
    public void findByLastNameContainingIgnoreCase() {
        log.info("... findByLastNameContainingIgnoreCase ...");

        List<ChessPlayer> players = playerRepo.findByLastNameContainingIgnoreCase("arl");
        assertThat(players.size()).isEqualTo(1);
        
        ChessPlayer player = players.get(0);
        log.info(player.getFirstName() + " " + player.getLastName()); 
    }

    @Test
    public void findByLastNameContainingIgnoreCaseAndFirstName() {
        log.info("... findByLastNameContainingIgnoreCaseAndFirstName ...");

        List<ChessPlayer> players = playerRepo.findByLastNameContainingIgnoreCaseAndFirstName("arl", "Magnus");
        assertThat(players.size()).isEqualTo(1);
        
        ChessPlayer player = players.get(0);
        log.info(player.getFirstName() + " " + player.getLastName()); 
    }

    @Test
    public void findByLastNameNotOrderByLastNameAscFirstNameAsc() {
        log.info("... findByLastNameNotOrderByLastNameAscFirstNameAsc ...");

        List<ChessPlayer> players = playerRepo.findByLastNameNotOrderByLastNameAscFirstNameAsc("Doe");
        assertThat(players.size()).isEqualTo(4);
        
        for (ChessPlayer player : players) {
            log.info(player.getFirstName() + " " + player.getLastName()); 
        }    
    }

    @Test
    public void findByTournamentsName() {
        log.info("... findByTournamentsName ...");

        List<ChessPlayer> players = playerRepo.findByTournamentsName("Tata Steel Chess Tournament 2021");
        assertThat(players.size()).isEqualTo(4);
        
        for (ChessPlayer player : players) {
            log.info(player.getFirstName() + " " + player.getLastName()); 
        }
    }
}
