package com.thorben.janssen.spring.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.thorben.janssen.spring.data.model.ChessPlayer;
import com.thorben.janssen.spring.data.model.ChessTournament;
import com.thorben.janssen.spring.data.repository.ChessPlayerRepository;
import com.thorben.janssen.spring.data.repository.ChessTournamentRepository;

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
public class TestFragmentRepository {

    private static final Logger log = LoggerFactory.getLogger(TestFragmentRepository.class);

    @Autowired
    private ChessPlayerRepository playerRepo;

    @Autowired
    private ChessTournamentRepository tournamentRepo;

    @Test
    public void fragmentRepository() {
        log.info("... fragmentRepository ...");

        List<ChessPlayer> players = playerRepo.getByTournamentName("Tata Steel Chess Tournament 2021");
        assertThat(players.size()).isEqualTo(4);
        
        for (ChessPlayer player : players) {
            log.info(player.getLastName() + ", " + player.getFirstName()); 
        }
    }
    
    @Test
    public void standardMethod() {
        log.info("... standardMethod ...");

        ChessPlayer player = playerRepo.findById(1L).get();
        assertThat(player.getId()).isEqualTo(1);
        
        log.info(player.getLastName() + ", " + player.getFirstName()); 
    }

    @Test
    public void derivedQuery() {
        log.info("... derivedQuery ...");

        List<ChessPlayer> players = playerRepo.findByTournamentsName("Tata Steel Chess Tournament 2021");
        assertThat(players.size()).isEqualTo(4);
        
        for (ChessPlayer player : players) {
            log.info(player.getLastName() + ", " + player.getFirstName()); 
        }
    }

    @Test
    public void complexRepository() {
        log.info("... complexRepository ...");

        log.info("findByName");
        ChessTournament tournament = tournamentRepo.findByName("Tata Steel Chess Tournament 2021");
        assertThat(tournament.getName()).isEqualTo("Tata Steel Chess Tournament 2021");

        log.info("findByPlayerId");
        List<ChessTournament> tournaments = tournamentRepo.findByPlayerId(1L);
        assertThat(tournaments.size()).isEqualTo(1);
    }
}
