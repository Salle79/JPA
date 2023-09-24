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
public class TestQueries {

    private static final Logger log = LoggerFactory.getLogger(TestQueries.class);

    @Autowired
    private ChessPlayerRepository playerRepo;

    @Test
    public void findByNameOrderedAdvancedLike() {
        log.info("... findByNameOrderedAdvancedLike ...");

        List<ChessPlayer> players = playerRepo.findByNameAndTournamentOrderedAdvancedLike("a", "a", "Tata Steel Chess Tournament 2021");
        assertThat(players.size()).isEqualTo(2);
        
        for (ChessPlayer player : players) {
            log.info(player.getFirstName() + " " + player.getLastName());
        }
    }
}
