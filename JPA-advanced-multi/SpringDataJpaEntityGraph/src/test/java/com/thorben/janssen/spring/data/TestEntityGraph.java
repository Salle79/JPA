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
public class TestEntityGraph {

    private static final Logger log = LoggerFactory.getLogger(TestEntityGraph.class);

    @Autowired
    private ChessPlayerRepository playerRepo;

    @Test
    public void namedEntityGraph() {
        log.info("... namedEntityGraph ...");

        List<ChessPlayer> players = playerRepo.findByLastName("Carlsen");
        assertThat(players.size()).isEqualTo(1);
        
        ChessPlayer player = players.get(0);
        log.info(player.getFirstName() + " " + player.getLastName()); 
    }

    @Test
    public void simpleCustomGraph() {
        log.info("... simpleCustomGraph ...");

        List<ChessPlayer> players = playerRepo.findWithSimpleGraphByLastName("Carlsen");
        assertThat(players.size()).isEqualTo(1);
        
        ChessPlayer player = players.get(0);
        log.info(player.getFirstName() + " " + player.getLastName()); 
    }

    @Test
    public void complexCustomGraph() {
        log.info("... complexCustomGraph ...");

        List<ChessPlayer> players = playerRepo.findWithComplexGraphByLastName("Carlsen");
        assertThat(players.size()).isEqualTo(1);
        
        ChessPlayer player = players.get(0);
        log.info(player.getFirstName() + " " + player.getLastName()); 
    }
}
