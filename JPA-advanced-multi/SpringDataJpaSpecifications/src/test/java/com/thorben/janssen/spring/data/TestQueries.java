package com.thorben.janssen.spring.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.thorben.janssen.spring.data.model.ChessPlayer;
import com.thorben.janssen.spring.data.repository.ChessPlayerRepository;
import com.thorben.janssen.spring.data.repository.ChessPlayerSpecs;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
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
    public void oneSpecification() {
        log.info("... oneSpecification ...");

        List<ChessPlayer> players = playerRepo.findAll(ChessPlayerSpecs.playedInTournament("Tata Steel Chess Tournament 2021"));

        // Validate and log result
        assertThat(players.size()).isEqualTo(4);
        for (ChessPlayer player : players) {
            log.info(player.getFirstName() + " " + player.getLastName()); 
        }
    }

    @Test
    public void countOneSpecification() {
        log.info("... countOneSpecification ...");

        long count = playerRepo.count(ChessPlayerSpecs.playedInTournament("Tata Steel Chess Tournament 2021"));

        // Validate and log result
        assertThat(count).isEqualTo(4);
    }

    @Test
    public void combineSpecifications() {
        log.info("... combineSpecifications ...");

        List<ChessPlayer> players = playerRepo.findAll(ChessPlayerSpecs.playedInTournament("Tata Steel Chess Tournament 2021")
                                                                       .and(ChessPlayerSpecs.hasFirstName("Magnus")
                                                                            .or(ChessPlayerSpecs.hasFirstName("Fabiano"))));

        // Validate and log result
        assertThat(players.size()).isEqualTo(2);
        for (ChessPlayer player : players) {
            log.info(player.getFirstName() + " " + player.getLastName()); 
        }
    }
}