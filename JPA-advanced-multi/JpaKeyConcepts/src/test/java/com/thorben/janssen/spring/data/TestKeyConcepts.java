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
public class TestKeyConcepts {

    private static final Logger log = LoggerFactory.getLogger(TestKeyConcepts.class);

    @Autowired
    private ChessPlayerRepository playerRepo;

    @Test
    public void testPersist() {
        log.info("... testPersist ...");

        ChessPlayer player = new ChessPlayer();
        player.setFirstName("Thorben");
        player.setLastName("Janssen");

        playerRepo.save(player);

        assertThat(playerRepo.findById(player.getId())).hasValue(player);
    }

    @Test
    public void testUpdate() {
        log.info("... testUpdate ...");

        ChessPlayer player = playerRepo.findById(1L).get();

        player.setFirstName("Sven Magnus");
    }

    @Test
    public void testFindByFirstName() {
        log.info("... testFindByFirstName ...");

        List<ChessPlayer> players = playerRepo.findByFirstName("Magnus");
        assertThat(players.size()).isEqualTo(1);
        
        ChessPlayer player = players.get(0);

        player.setFirstName("Sven Magnus");     

        log.info(player.getFirstName() + " " + player.getLastName() + " played " 
                 + player.getGamesWhite().size() + " game with the white pieces."); 
    }
}
