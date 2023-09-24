package com.thorben.janssen.spring.data;

import java.util.List;
import java.util.logging.Logger;

import com.thorben.janssen.spring.data.model.ChessPlayer;
import com.thorben.janssen.spring.data.repository.ChessPlayerRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class TestRepository {
    
    Logger log = Logger.getLogger(TestRepository.class.getName());

    @Autowired
    private ChessPlayerRepository playerRepo;

    @Test
    @Sql("/test-data.sql")
    public void testQuery() {
        log.info("... testQuery ...");

        List<ChessPlayer> players = playerRepo.findByLastName("Carlsen");
        log.info("Found "+players.size()+" players.");
    }

    @Test
    @Commit
    public void testTransaction() {
        log.info("... testTransaction ...");

        ChessPlayer player = new ChessPlayer();
        player.setFirstName("Thorben");
        player.setLastName("Janssen");
        playerRepo.saveAndFlush(player);
    }
}
