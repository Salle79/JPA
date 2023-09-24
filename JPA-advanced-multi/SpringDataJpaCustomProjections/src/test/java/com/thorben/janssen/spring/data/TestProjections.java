package com.thorben.janssen.spring.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.thorben.janssen.spring.data.model.ChessPlayer;
import com.thorben.janssen.spring.data.model.PlayerFullNameIntf;
import com.thorben.janssen.spring.data.model.PlayerName;
import com.thorben.janssen.spring.data.model.PlayerNameIntf;
import com.thorben.janssen.spring.data.model.TournamentIntf;
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
public class TestProjections {

    private static final Logger log = LoggerFactory.getLogger(TestProjections.class);

    @Autowired
    private ChessPlayerRepository playerRepo;

    @Autowired
    private ChessTournamentRepository tournamentRepo;

    @Test
    public void entityProjection() {
        log.info("... entityProjection ...");

        List<ChessPlayer> players = playerRepo.findByName("Carlsen");
        assertThat(players.size()).isEqualTo(1);
        
        for (ChessPlayer player : players) {
            log.info(player.getLastName() + ", " + player.getFirstName()); 
        }
    }

    @Test
    public void scalarProjection() {
        log.info("... scalarProjection ...");

        List<Object[]> playerNames = playerRepo.getPlayerNames();
        
        for (Object[] names : playerNames) {
            log.info(names[1] + ", " + names[0]); 
        }
    }

    @Test
    public void constructorExpression() {
        log.info("... constructorExpression ...");

        List<PlayerName> playerNames = playerRepo.getPlayerNamesConstrutorExpression();
        
        for (PlayerName names : playerNames) {
            log.info(names.getLastName() + ", " + names.getFirstName()); 
        }
    }

    @Test
    public void derivedQueryDtoClass() {
        log.info("... derivedQueryDtoClass ...");

        List<PlayerName> playerNames = playerRepo.findPlayerNamesBy();
        
        for (PlayerName names : playerNames) {
            log.info(names.getLastName() + ", " + names.getFirstName()); 
        }
    }

    @Test
    public void derivedQueryDtoInterface() {
        log.info("... derivedQueryDtoInterface ...");

        List<PlayerNameIntf> playerNames = playerRepo.findPlayerNamesIntfBy();
        
        for (PlayerNameIntf names : playerNames) {
            log.info(names.getLastName() + ", " + names.getFirstName());
            log.info(names.getClass().getName());
        }
    }

    @Test
    public void nestedAssociations() {
        log.info("... nestedAssociations ...");

        TournamentIntf tournament = tournamentRepo.findByName("Tata Steel Chess Tournament 2021");
        
        log.info(tournament.getName());
        for (PlayerNameIntf player : tournament.getPlayers()) {
            log.info(" - " + player.getLastName() + ", " + player.getFirstName());
        }
    }

    @Test
    public void nestedAssociationsJoinFetch() {
        log.info("... nestedAssociationsJoinFetch ...");

        TournamentIntf tournament = tournamentRepo.findByNameWithPlayers("Tata Steel Chess Tournament 2021");
        
        log.info(tournament.getName());
        for (PlayerNameIntf player : tournament.getPlayers()) {
            log.info(" - " + player.getLastName() + ", " + player.getFirstName());
        }
    }

    @Test
    public void expressionLanguage() {
        log.info("... expressionLanguage ...");

        List<PlayerFullNameIntf> playerNames = playerRepo.findPlayerFullNamesBy();
        
        for (PlayerFullNameIntf player : playerNames) {
            log.info(player.getFullName());
        }
    }

    @Test
    public void dynamicEntityProjection() {
        log.info("... dynamicEntityProjection ...");

        ChessPlayer player = playerRepo.findPlayerByLastName("Carlsen", ChessPlayer.class);
        
        log.info(player.getLastName() + ", " + player.getFirstName());
    }

    @Test
    public void dynamicDtoProjection() {
        log.info("... dynamicDtoProjection ...");

        PlayerNameIntf player = playerRepo.findPlayerByLastName("Carlsen", PlayerNameIntf.class);
        
        log.info(player.getLastName() + ", " + player.getFirstName());
    }
}
